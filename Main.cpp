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

template <class AnimalType>
void loadData(list<AnimalType>& animals, string path) {
  ifstream file(path);
  string fields[8];
  string line;
  if(!file){
    throw runtime_error("File has not been opened correctly.");
  }
  else{

    while (file >> line) {
      int fieldNo = 0;
      int position = line.find(',');

      while (position != string::npos) {
        fields[fieldNo] = line.substr(0, position);
        line.erase(0, position + 1);
        position = line.find(',');
        fieldNo++;
      }

      fields[7] = line;

      AnimalType* father = NULL;
      AnimalType* mother = NULL;

      if (fields[6].length() > 0) {
        for (AnimalType& animal : animals) {
          if (animal.getName() == fields[6]) {
            father = &animal;
          }
        }
      }

      if (fields[7].length() > 0) {
        for (AnimalType& animal : animals) {
          if (animal.getName() == fields[7]) {
            mother = &animal;
          }
        }
      }

      animals.push_back(AnimalType(fields[1], fields[0], fields[2], fields[3],
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


template <class AnimalType>
void printList(const list<AnimalType>& animals) {
  for (AnimalType animal : animals) {
    string fatherstring;
    string motherstring;

    if(animal.getFather() == NULL){
      fatherstring = "N/A";
    }
    else{
      fatherstring = animal.getFather()->getName().c_str();
    }

    if(animal.getMother() == NULL){
      motherstring = "N/A";
    }
    else{
      motherstring = animal.getMother()->getName().c_str();
    }

    cout << left;
    cout << setw(11) << animal.getName().c_str();
    cout << setw(8) << animal.getAnimalType().c_str();
    cout << setw(11) << animal.getBreed().c_str();
    cout << setw(11) << animal.getColour().c_str();
    cout << setw(11) << animal.getEarType().c_str();
    cout << setw(8) << animal.getHeight().c_str();
    cout << setw(13) << animal.getTailColour().c_str();
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

template <class AnimalType>
bool findAnimal(const list<AnimalType> animals, const char* name) {
  bool found = false;
  for (AnimalType animal : animals) {
    if (animal.getName() == name) {
      printf("\n%s is found in the %s inventory. \nPaternal tree of %s: \n",
             name, animal.getAnimalType().c_str(), name);

      animal.printPaternalTree();
      found = true;
    }
  }

  return found;
}

int main() {
  try{
  list<Cat> cats;
  list<Dog> dogs;
  list<Horse> horses;

  loadData(cats, "data/cats.csv");
  loadData(dogs, "data/dogs.csv");
  loadData(horses, "data/horses.csv");

  printf("There are %1ld dog(s), %1ld cat(s) and %1ld horse(s) in the "
         "inventory, which are:\n\n", dogs.size(), cats.size(), horses.size());

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

          printf("%s was not found in any inventory!", name);
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
}
catch(const exception& e){
  cerr << "Exception Caught: "<< e.what() << endl;
}

  return 0;
}
