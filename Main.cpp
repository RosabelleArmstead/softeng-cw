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

using namespace std;


template <class T>
void loadData(list<T>& animals, string path) {
  ifstream file(path);
  string fields[8];
  string line;
  if(!file){
    throw runtime_error("File has not been opened correctly.");

  } else{
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
/*
void printHeader() {
  printf("%-10s %-7s %-10s %-10s %-10s %-7s %-12s %-10s %-10s\n", "Name",
         "Group", "Breed", "Colour", "Ear Type", "Height", "Tail Colour",
         "Dad", "Mom");
  cout << "------------------------------------------------------------------";
  cout << "-------------------------" << endl;
}
*/
void printHeader() {
  cout << left;
  cout << setw(11) << "Name";
  cout << setw(8) << "Group";
  cout << setw(11) << "Breed";
  cout << setw(11) << "Colour";
  cout << setw(11) << "Ear Type";
  cout << setw(8) << "Height";
  cout << setw(13) << "Tail Colour";
  cout << setw(11) << "Dad";
  cout << setw(11) << "Mom" << endl;
  cout << "------------------------------------------------------------------";
  cout << "-------------------------" << endl;
}


template <class T>
void printList(const list<T>& animals) {
  for (typename list<T>::const_iterator i = animals.begin();
       i != animals.end(); ++i) {

    string fatherstring = "N/A";
    string motherstring = "N/A";

    if(i->getFather() != NULL){
      fatherstring = i->getFather()->getName();
    }

    if(i->getMother() != NULL){
      motherstring = i->getMother()->getName();
    }

    cout << left;
    cout << setw(11) << i->getName();
    cout << setw(8) << i->getAnimalType();
    cout << setw(11) << i->getBreed();
    cout << setw(11) << i->getColour();
    cout << setw(11) << i->getEarType();
    cout << setw(8) << i->getHeight();
    cout << setw(13) << i->getTailColour();
    cout << setw(11) << fatherstring;
    cout << setw(11) << motherstring << endl;


    /*
    printf("%-10s %-7s %-10s %-10s %-10s %-7s %-12s %-10s %-10s\n",
           animal.getName().c_str(),
           animal.getAnimalType().c_str(),
           animal.getBreed().c_str(),
           animal.getColour().c_str(),
           animal.getEarType().c_str(),
           animal.getHeight().c_str(),
           animal.getTailColour().c_str(),
           animal.getFather() == NULL ? "N/A" :
             animal.getFather()->getName().c_str(),
           animal.getMother() == NULL ? "N/A" :
             animal.getMother()->getName().c_str());
             */
  }

  cout << endl;
}

template <class T>
bool findAnimal(const list<T> animals, const char* name) {
  bool found = false;
  for (typename list<T>::const_iterator i = animals.begin();
       i != animals.end(); ++i) {

    if (i->getName() == name) {
  /*    printf("\n%s is found in the %s inventory. \nPaternal tree of %s: \n",
             name, animal.getAnimalType().c_str(), name);
*/
      cout << name << " is found in the " << i->getAnimalType() << " inventory." << endl;
      cout << "Paternal tree of " << name << endl;

      i->printPaternalTree();
      found = true;
    }
  }

  return found;
}

int main() {
  list<Cat> cats;
  list<Dog> dogs;
  list<Horse> horses;

  try{
    loadData(cats, "data/cats.csv");
    loadData(dogs, "data/dogs.csv");
    loadData(horses, "data/horses.csv");
  } catch(const exception& e) {
    cerr << "Exception caught: "<< e.what() << endl;
  }

  //printf("There are %1ld dog(s), %1ld cat(s) and %1ld horse(s) in the "
    //     "inventory, which are:\n\n", dogs.size(), cats.size(), horses.size());
  cout << "There are " << dogs.size() << " dog(s), " << cats.size() <<
    " cat(s) and " << horses.size() << " horse(s) in the inventory, which are:" << endl<< endl;

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
      const char* name = query.substr(2).c_str();

      if (type == 'a') {
        if (!findAnimal<Dog>(dogs, name) && !findAnimal<Cat>(cats, name) &&
            !findAnimal<Horse>(horses, name)) {

          //printf("%s was not found in any inventory!", name);
          cout << name << " was not found in any inventory";
        }
      } else if (type == 'd' && !findAnimal<Dog>(dogs, name)) {
        printf("%s was not found in the inventory within the dogs!", name);
      } else if (type == 'c' && !findAnimal<Cat>(cats, name)) {
        printf("%s was not found in the inventory within the cats!", name);
      } else if (type == 'h' && !findAnimal<Horse>(horses, name)) {
        printf("%s was not found in the inventory within the horses!", name);
      } else if (type != 'a' && type != 'd' && type != 'c' && type != 'h') {
        cout << "Sorry, that is not a valid query. Please try again.";
      }
    }
  }

  return 0;
}
