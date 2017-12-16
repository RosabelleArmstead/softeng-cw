// Main.cpp
#include "Animal.h"
#include "Cat.h"
#include "Dog.h"
#include "Horse.h"
#include <algorithm>
#include <exception>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <list>
#include <sstream>
#include <string>

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

// Input   : None.
// Purpose : Reads animal data files and provides interface for user, with input validation and error
//           handling. Uses helper methods defined in this file.
// Output  : Command interface for user to search and view the animal inventories.
// Return  : 0.
// Throw   : None. Anticipated exceptions thrown inside this function are caught and handled within.
int main() {
  list<Cat> cats;
  list<Dog> dogs;
  list<Horse> horses;

  try{
    loadData(cats, "data/Cats.csv");
    loadData(dogs, "data/Dogs.csv");
    loadData(horses, "data/Horses.csv");

  } catch(const exception& e) {  // File could not be opened or was invalid.
    cerr << "Exception caught: " << e.what() << endl;
  }  // Continue with any Animals that were successfully created before exception was thrown.

  cout << "There are " << dogs.size() << " dog(s), " << cats.size() << " cat(s) and "
       << horses.size() << " horse(s) in the inventory, which are:" << endl << endl;

  printHeader();  // Only print headers once (as per coursework assignment).
  printList(dogs);
  printList(cats);
  printList(horses);

  bool exited = false;
  while (!exited) {  // User has not yet entered 'exit'.
    string input;
    cout << endl << endl;
    cout << "Enter the first letter of the animal group and the name of the specified one to find "
            "its paternal tree (or type exit): ";

    getline(cin, input);  // Read one line from user input (terminated by newline).
    trimString(input);  // Remove whitespace at start and end of input.
    toLower(input);  // Convert input to lowercase.

    if (input == "exit") {
      cout << endl << endl << "Goodbye!" << endl;
      exited = true;  // Set exited to true so that the program loop ends.

    } else {
      try {
        if (input.length() < 3) {
          // Minimum input after trimming is: inventory code, followed by space, then a single letter.
          throw invalid_argument("Input must be at least three characters long.");

        } else if (!isspace(input.at(1))) {
          // After trimming, there should be a single character followed be some whitespace.
          // For example, the user could use tabs to separate inventory code and query instead of space(s).
          throw invalid_argument("Input must be an inventory code followed by the search query.");
        }

        char type = input.at(0);
        string query = input.substr(2);
        // Remove any extra space that was between inventory code and query. Name MAY include spaces.
        // For example, 'c  \t\t\t  charlie the cat' will give the query 'charlie the cat'.
        trimString(query);

        // Ensure that the inventory type--the first character of input after trimming and converting to
        // lowercase--is either 'a', 'd', 'c' or 'h'.
        if (type != 'a' && type != 'd' && type != 'c' && type != 'h') {
            throw invalid_argument("Input must start with a valid inventory code.");

        } else if (type == 'a' && !(findAnimal<Dog>(dogs, query) || findAnimal<Cat>(cats, query) ||
                                    findAnimal<Horse>(horses, query))) {

          // findAnimal only returns true if one or more matches(s) was/were found.
          cout << query << " was not found in any inventory!";

        } else if (type == 'd' && !findAnimal<Dog>(dogs, query)) {
          cout << query << " was not found in the inventory within the dogs!";

        } else if (type == 'c' && !findAnimal<Cat>(cats, query)) {
          cout << query << " was not found in the inventory within the cats!";

        } else if (type == 'h' && !findAnimal<Horse>(horses, query)) {
          cout << query << " was not found in the inventory within the horses!";

        }
      } catch (invalid_argument& e) {  // Catch any invalid argument errors and inform user.
        cerr << "Sorry, an error occurred: " << e.what();
      }
    }
  }

  return 0;
}

// Input   : A list to which Animals of a particular type, T, will be added and the path of the data file
//           from which animal data will be read, passed by reference.
// Purpose : Creates and adds Animals of type T to a list of type T, read from the file specified by 'path'.
// Output  : None.
// Return  : None.
// Throw   : runtime_error if the file could not be opened or the provided file has too many fields per
//           record.
template <class T>
void loadData(list<T>& animals, const string& path) {
  ifstream file(path.c_str());  // ifstream only takes C strings for C++ versions prior to C++11.

  if (file.is_open()) {  // Check that file was opened correctly.
    string record;

    // Read one line at a time into record.
    while (getline(file, record)) {
      string fields[8];  // Initialise empty array for record's fields (only eight for animal CSVs).
      int fieldNo = 0;

      // Use an istringstream so we can read by delimiter using getline (as a character stream).
      // istringstream is for input streams only. Offers extra compiler checks than in/out streams and MAY be
      // more efficient. More appropriate for our requirements.
      istringstream ss(record);
      string field;

      while (getline(ss, field, ',')) {  // Use ',' as the delimiter.
        if (fieldNo > 7) {  // Valid animal data files should only have eight fields. Avoid seg fault 11.
          throw new runtime_error("Record has too many fields!");
        }

        // Remove whitespace from field, including possible leftover CR or LF characters.
        // Also ensures that name matching will work as expected if there are any animal names with spaces.
        // E.g. ' some name' in the CSV MUST be translated to 'some name' for the findAnimal method to work.
        trimString(field);

        fields[fieldNo] = field;
        fieldNo++;
      }

      // Set pointers to null by default, indicating that Animal has no parents.
      const T* father = NULL;
      const T* mother = NULL;

      if (fields[6].length() > 0) {  // Animal has father.
        for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
          // If existing Animal's getName matches the father field for this record, set father pointer to the
          // matching Animal's memory address.
          if (i->getName() == fields[6]) { father = &*i; }
        }
      }

      if (fields[7].length() > 0) {  // Animal has mother.
        for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
          if (i->getName() == fields[7]) { mother = &*i; }
        }
    }

      // Create new Animal using the record's data and add it to the Animal list.
      animals.push_back(T(fields[1], fields[0], fields[2], fields[3], fields[4], fields[5], father, mother));
    }

    file.close();  // Ensure that the file is closed.
  } else {
    throw runtime_error("File has not been opened correctly.");
  }
}

// Input   : None.
// Purpose : Helper function to output headings for Animal table.
// Output  : Output headings for the table of Animals.
// Return  : None.
// Throw   : None.
void printHeader() {
  cout << left << setw(11) << "Name" << setw(8) << "Group" << setw(11) << "Breed" << setw(11) << "Colour"
       << setw(11) << "Ear Type" << setw(8) << "Height" << setw(13) << "Tail Colour" << setw(11) << "Dad"
       << setw(11) << "Mom" << endl << "------------------------------------------------------------------"
       << "-------------------------" << endl;
}

// Input   : Animal list of type T, by reference.
// Purpose : Helper method which takes a list of Animals and outputs their attributes with column formatting.
// Output  : Outputs Animal attributes.
// Return  : None.
// Throw   : None.
template <class T>
void printList(const list<T>& animals) {
  // Iterate through list and output attributes for each Animal.
  for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
    cout << left << setw(11) << i->getName()
         << setw(8) << i->getAnimalType()
         << setw(11) << i->getBreed()
         << setw(11) << i->getColour()
         << setw(11) << i->getEarType()
         << setw(8) << i->getHeight()
         << setw(13) << i->getTailColour()
         // If parent pointers are null, output 'N/A'. Otherwise, output parents' names.
         << setw(11) << (i->getFather() == NULL ? "N/A" : i->getFather()->getName())
         << setw(11) << (i->getMother() == NULL ? "N/A" : i->getMother()->getName()) << endl;
  }

  cout << endl;
}

// Input   : Animal list to search by reference, lowercase query by reference.
// Purpose : Outputs paternal tree of any Animals whose name matches the query. Case insensitive.
// Output  : Name of and paternal tree for any matching Animals.
// Return  : True if any matching Animals found.
// Throw   : None.
template <class T>
bool findAnimal(const list<T>& animals, const string& query) {
  bool found = false;
  // Iterate through the list of Animals provided using a const_iterator.
  for (typename list<T>::const_iterator i = animals.begin(); i != animals.end(); ++i) {
    string animalName = i->getName();
    toLower(animalName);  // Use lowercase animal names for comparing to lowercase query.

    // If query matches an existing Animal name (in lowercase), output the Animal's name, the inventory it is
    // in and its paternal tree.
    if (animalName == query) {
      cout << i->getName() << " is found in the " << i->getAnimalType() << " inventory." << endl;
      cout << "Paternal tree of " << i->getName() << endl;
      i->printPaternalTree();

      // Remember that a result was found; continue searching in case Animals share the same name.
      found = true;
    }
  }

  return found;
}

// Input   : String to be converted to lowercase, by reference.
// Purpose : Converts string's characters to lowercase (in place).
// Output  : None.
// Return  : None.
// Throw   : None.
void toLower(string& text) {
  // Use transform from the Algorithm library (STL) with tolower.
  transform(text.begin(), text.end(), text.begin(), ::tolower);
}

// Input   : String to be trimmed, by reference.
// Purpose : Removes whitespace from left and right of string (in place).
// Output  : None.
// Return  : None.
// Throw   : None.
void trimString(string& text) {
  // Remove from character 0 to first character (not inclusive) that is not whitespace. Trim left.
  text.erase(0, text.find_first_not_of(" \t\n\r\f\v"));

  // Remove everything after the last non-whitespace character (hence the '+1'). Trim right.
  text.erase(text.find_last_not_of(" \t\n\r\f\v") + 1);
}
