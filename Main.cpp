#include "Animal.h"
#include "Cat.h"
#include "Dog.h"
#include "Horse.h"
#include <fstream>
#include <iostream>
#include <list>
#include <string>
#include <stdio.h>
#include <exception>
#include <iomanip>

template <class T>
void loadData(list<T>& animals, string path);
void printHeader();
template <class T>
void printList(const list<T>& animals);
template <class T>
bool findAnimal(const list<T>& animals, const string& name);
void toLower(string& text);

using namespace std;

int main() {
  list<Cat> cats;
  list<Dog> dogs;
  list<Horse> horses;

  try{
    loadData(cats, "data/cats.csv");
    loadData(dogs, "data/dogs.csv");
    loadData(horses, "data/horses.csv");

  } catch(const exception& e) {
    cerr << "Exception caught: " << e.what() << endl;
  }

  cout << "There are " << dogs.size() << " dog(s), " << cats.size() <<
          " cat(s) and " << horses.size() << " horse(s) in the inventory,"
          " which are:" << endl << endl;

  printHeader();
  printList(dogs);
  printList(cats);
  printList(horses);

  bool exited = false;
  while (!exited) {
    string query;
    cout << endl << endl;
    cout << "Enter the first letter of the animal group and the name of the "
            "specified one to find its paternal tree (or type exit): ";
    getline(cin, query);

    if (query == "exit") {
      cout << endl << endl << "Goodbye!" << endl;
      exited = true;

    } else if (query.length() < 3 || query.at(1) != ' ') {
      cout << "Sorry, that is not a valid query. Please try again.";

    } else {
      char type = query.at(0);
      string name = query.substr(2);

      if (type == 'a' && !findAnimal<Dog>(dogs, name) &&
                 !findAnimal<Cat>(cats, name) &&
                 !findAnimal<Horse>(horses, name)) {

        cout << name << " was not found in any inventory";

      } else if (type == 'd' && !findAnimal<Dog>(dogs, name)) {
        cout << name << " was not found in the inventory within the dogs!";

      } else if (type == 'c' && !findAnimal<Cat>(cats, name)) {
        cout << name << " was not found in the inventory within the cats!";

      } else if (type == 'h' && !findAnimal<Horse>(horses, name)) {
        cout << name << " was not found in the inventory within the horses!";

      } else if (type != 'a' && type != 'd' && type != 'c' && type != 'h') {
        cout << "Sorry, that is not a valid query. Please try again.";
      }
    }
  }

  return 0;
}

template <class T>
void loadData(list<T>& animals, string path) {
  ifstream file(path);
  string fields[8];
  string line;

  if (!file) {
    throw runtime_error("File has not been opened correctly.");
  } else {
    while (file >> line) {
      int fieldNo = 0;
      int position = line.find(',');

      while (position != -1) {
        fields[fieldNo] = line.substr(0, position);
        line.erase(0, position + 1);
        position = line.find(',');
        fieldNo++;
      }

      fields[7] = line;

      const T* father = NULL;
      const T* mother = NULL;

      if (fields[6].length() > 0) {
        for (typename list<T>::const_iterator i = animals.begin();
             i != animals.end(); ++i) {
          if (i->getName() == fields[6]) { father = &*i; }
        }
      }

      if (fields[7].length() > 0) {
        for (typename list<T>::const_iterator i = animals.begin();
             i != animals.end(); ++i) {
          if (i->getName() == fields[7]) { mother = &*i; }
        }
      }

      animals.push_back(T(fields[1], fields[0], fields[2], fields[3],
                          fields[4], fields[5], father, mother));
    }
  }
}

void printHeader() {
  cout << left << setw(11) << "Name" << setw(8) << "Group" << setw(11)
       << "Breed" << setw(11) << "Colour" << setw(11) << "Ear Type" << setw(8)
       << "Height" << setw(13) << "Tail Colour" << setw(11) << "Dad"
       << setw(11) << "Mom" << endl
       << "------------------------------------------------------------------"
       << "-------------------------" << endl;
}

template <class T>
void printList(const list<T>& animals) {
  for (typename list<T>::const_iterator i = animals.begin();
       i != animals.end(); ++i) {

    cout << left << setw(11) << i->getName()
         << setw(8) << i->getAnimalType()
         << setw(11) << i->getBreed()
         << setw(11) << i->getColour()
         << setw(11) << i->getEarType()
         << setw(8) << i->getHeight()
         << setw(13) << i->getTailColour()
         << setw(11)
         << (i->getFather() == NULL ? "N/A" : i->getFather()->getName())
         << setw(11)
         << (i->getMother() == NULL ? "N/A" : i->getMother()->getName())
         << endl;
  }

  cout << endl;
}

template <class T>
bool findAnimal(const list<T>& animals, const string& name) {
  bool found = false;
  for (typename list<T>::const_iterator i = animals.begin();
       i != animals.end(); ++i) {
    string animalName = i->getName();

    if (animalName == name) {
      cout << i->getName() << " is found in the " << i->getAnimalType()
           << " inventory." << endl;

      cout << "Paternal tree of " << i->getName() << endl;
      i->printPaternalTree();

      found = true;
    }
  }

  return found;
}
