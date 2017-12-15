// Main.cpp
#include "Animal.h"
#include "Cat.h"
#include "Dog.h"
#include "Horse.h"
#include <fstream>
#include <iostream>
#include <list>
#include <string>
#include <exception>
#include <iomanip>
#include <algorithm>
#include <sstream>

using namespace std;

// Declare methods implemented in this file.
template <class T>
void loadData(list<T>& animals, const string& path);
void printHeader();
template <class T>
void printList(const list<T>& animals);
template <class T>
bool findAnimal(const list<T>& animals, const string& name);
void toLower(string& text);
void trimString(string& text);

// Input   : none.
// Purpose : reads animal data files and provides interface for user, with input validation and
//           error handling. Uses helper methods defined in this file.
// Output  : command interface for user to search and view the animal inventories.
// Return  : 0.
int main() {
  list<Cat> cats;
  list<Dog> dogs;
  list<Horse> horses;

  try{
    loadData(cats, "data/cats.csv");
    loadData(dogs, "data/dogs.csv");
    loadData(horses, "data/horses.csv");

  } catch(const exception& e) {  // File could not be opened or was invalid.
    cerr << "Exception caught: " << e.what() << endl;
  }  // Continue with any files/animals that successfully loaded before exception was thrown.

  cout << "There are " << dogs.size() << " dog(s), " << cats.size() << " cat(s) and "
       << horses.size() << " horse(s) in the inventory, which are:" << endl << endl;

  printHeader();  // Only print headers once (as per coursework assignment).
  printList(dogs);
  printList(cats);
  printList(horses);

  bool exited = false;
  while (!exited) {  // User has not entered 'exit'.
    string input;
    cout << endl << endl;
    cout << "Enter the first letter of the animal group and the name of the specified one to find "
            "its paternal tree (or type exit): ";

    getline(cin, input);  // Read one line from user input (terminated by newline).
    trimString(input);  // Remove whitespace at start and end of input.
    toLower(input);  // Convert input to lowercase.

    if (input == "exit") {
      cout << endl << endl << "Goodbye!" << endl;
      exited = true;  // Set exited to true so the user can exit the program (end program loop).

    } else {
      try {
        if (input.length() < 3) {
          // Minimum input (after trimming) is inventory code, space, then a single letter (3).
          throw invalid_argument("Input must be at least three characters long.");

        } else if (!isspace(input.at(1))) {
          // After trimming, there should be a single character followed be some whitespace.
          // E.g. the user could use tabs to separate inventory code and query instead of space(s).
          throw invalid_argument("Input must be an inventory code followed by the search query.");
        }

        char type = input.at(0);
        string query = input.substr(2);
        // Remove any extra space that was between inventory code and query. Name MAY include
        // spaces, such as 'c charlie the cat', giving the query 'charlie the cat'.
        trimString(query);

        if (type != 'a' && type != 'd' && type != 'c' && type != 'h') {
            throw invalid_argument("Input must start with a valid inventory code.");

        } else if (type == 'a' && !(findAnimal<Dog>(dogs, query) || findAnimal<Cat>(cats, query) ||
                                    findAnimal<Horse>(horses, query))) {

          // findAnimal returns true if one or more matches(s) was/were found.
          cout << query << " was not found in any inventory";

        } else if (type == 'd' && !findAnimal<Dog>(dogs, query)) {
          cout << query << " was not found in the inventory within the dogs!";

        } else if (type == 'c' && !findAnimal<Cat>(cats, query)) {
          cout << query << " was not found in the inventory within the cats!";

        } else if (type == 'h' && !findAnimal<Horse>(horses, query)) {
          cout << query << " was not found in the inventory within the horses!";

        }
      } catch (invalid_argument& e) {  // Catch any invalid argument errors.
        cerr << "Sorry, an error occurred: " << e.what();
      }
    }
  }

  return 0;
}

template <class T>
void loadData(list<T>& animals, const string& path) {
  ifstream file(path.c_str());  // ifstream only takes C strings for C++ versions before C++11.

  if (file.is_open()) {  // Check that file was opened correctly.
    string record;

    // Read one line at a time into 'record'. '\n' is the default delimiter.
    while (getline(file, record)) {
      string fields[8];  // Initialise empty array for record's fields (only eight for animal CSVs).
      int fieldNo = 0;

      // Use an istringstream so we can read by delimiter using getline (as a character stream).
      istringstream ss(record);
      string field;

      while (getline(ss, field, ',')) {  // Use ',' as the delimiter.
        if (fieldNo > 7) {  // Invalid CSV file -- has too many fields for an animal data file.
          throw new runtime_error("Record has too many fields!");
        }

        fields[fieldNo] = field;
        fieldNo++;
      }

      const T* father = NULL;  // Default pointers to null (i.e. no parent exists).
      const T* mother = NULL;

      if (fields[6].length() > 0) {  // Animal has father.
        for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
          // If existing Animal's getName matches the father field for this record, set father 
          // pointer to the matching Animal's address.
          if (i->getName() == fields[6]) { father = &*i; }
        }
      }

      if (fields[7].length() > 0) {  // Animal has mother.
        for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
          // If existing Animal's getName matches the mother field for this record, set mother 
          // pointer to the matching Animal's address.
          if (i->getName() == fields[7]) { mother = &*i; }
        }
    }

      // Create new animal using the record's data and add it to the animal list.
      animals.push_back(T(fields[1], fields[0], fields[2], fields[3], fields[4], fields[5], father,
                          mother));
    }

    file.close();  // Ensure that the file is closed.
  } else {
    throw runtime_error("File has not been opened correctly.");
  }
}

// Input   : none.
// Purpose : helper function to print headings for Animal lists.
// Output  : prints headings for Animal lists.
// Return  : none.
void printHeader() {
  cout << left << setw(11) << "Name" << setw(8) << "Group" << setw(11)
       << "Breed" << setw(11) << "Colour" << setw(11) << "Ear Type" << setw(8)
       << "Height" << setw(13) << "Tail Colour" << setw(11) << "Dad"
       << setw(11) << "Mom" << endl
       << "------------------------------------------------------------------"
       << "-------------------------" << endl;
}

// Input   : Animal list by reference.
// Purpose : prints Animal details with formatted columns.
// Output  : prints formatted Animal details.
// Return  : none.
template <class T>
void printList(const list<T>& animals) {
  for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
    cout << left << setw(11) << i->getName()
         << setw(8) << i->getAnimalType()
         << setw(11) << i->getBreed()
         << setw(11) << i->getColour()
         << setw(11) << i->getEarType()
         << setw(8) << i->getHeight()
         << setw(13) << i->getTailColour()
         // If parent pointers are null, output 'N/A' instead. Otherwise, output parents' names.
         << setw(11) << (i->getFather() == NULL ? "N/A" : i->getFather()->getName())
         << setw(11) << (i->getMother() == NULL ? "N/A" : i->getMother()->getName()) << endl;
  }

  cout << endl;
}

// Input   : Animal list to search by reference, lowercase query by reference.
// Purpose : prints paternal tree of any Animals whose name matches the query. Case insensitive.
// Output  : name of and paternal tree for any matching Animals.
// Return  : true if any matching Animals found.
template <class T>
bool findAnimal(const list<T>& animals, const string& query) {
  bool found = false;
  for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
    string animalName = i->getName();
    toLower(animalName);  // Use lowercase Animal names for comparing to lowercase query.

    if (animalName == query) {
      cout << i->getName() << " is found in the " << i->getAnimalType() << " inventory." << endl;
      cout << "Paternal tree of " << i->getName() << endl;
      i->printPaternalTree();

      // Remember that a result was found; continue searching in case Animals share a name.
      found = true;
    }
  }

  return found;
}

// Input   : string to be converted to lowercase by reference.
// Purpose : converts string's characters to lowercase in place.
// Output  : none.
// Return  : none.
void toLower(string& text) {
  // Use transform from the Algorithm library (STL) with tolower.
  transform(text.begin(), text.end(), text.begin(), ::tolower);
}

// Input   : string to be converted to be trimmed by reference.
// Purpose : removes whitespace from left and right of string in place.
// Output  : none.
// Return  : none.
void trimString(string& text) {
  // Remove from character 0 to first character (not inclusive) that is not whitespace. Trim left.
  text.erase(0, text.find_first_not_of(" \t\n\r\f\v"));

  // Remove everything after (hence the '+1') the last non-whitespace character. Trim right.
  text.erase(text.find_last_not_of(" \t\n\r\f\v") + 1);
}
