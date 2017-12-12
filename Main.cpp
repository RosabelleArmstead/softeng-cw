#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <list>
#include <iomanip>
#include "Animal.h"
#include "Cat.h"

using namespace std;

template <class AnimalType>
list<AnimalType> loadData(string path) {
  ifstream file(path);
  list<AnimalType> animals;
  string fields[8];
  string line;

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
      for (Animal& animal : animals) {
        if (animal.getName() == fields[6]) {
          father = &animal;
        }
      }
    }

    if (fields[7].length() > 0) {
      for (Animal& animal : animals) {
        if (animal.getName() == fields[7]) {
          mother = &animal;
        }
      }
    }

    animals.push_back(AnimalType(fields[1], fields[0], fields[2], fields[3], fields[4], fields[5], father, mother));
  }

  return animals;
}

template <class AnimalType>
void printList(list<AnimalType> animals) {
  cout << left;
  cout << setw(10) << "Name";
  cout << setw(10) << "Group";
  cout << setw(10) << "Breed";
  cout << setw(10) << "Colour";
  cout << setw(10) << "Ear Type";
  cout << setw(10) << "Height";
  cout << setw(15) << "Tail Colour";
  cout << setw(10) << "Dad";
  cout << setw(10) << "Mom" << endl;
  cout << "-----------------------------------------------------------------------------------------" << endl;

  for (Animal animal : animals) {
    cout << left;
    cout << setw(10) << animal.getName();
    cout << setw(10) << animal.getAnimalType();
    cout << setw(10) << animal.getBreed();
    cout << setw(10) << animal.getColour();
    cout << setw(10) << animal.getEarType();
    cout << setw(10) << animal.getHeight();
    cout << setw(15) << animal.getTailColour();
    string father = "N/A";
    string mother = "N/A";
    if (animal.getFather() != NULL) { father = animal.getFather()->getName(); }
    if (animal.getMother() != NULL) { mother = animal.getMother()->getName(); }
    cout << setw(10) << father;
    cout << setw(10) << mother << endl;
  }
}

int main() {
  try{
  list<Cat> cats = loadData<Cat>("data/cats.csv");
} catch(invalid_argument& e){
  cerr << "Invalid Argument:" << e.what() << endl;
}

  printList(cats);

  cout << endl << endl << "Paternal tree for " << cats.back().getName() << endl;
  cout << cats.back().getPaternalTree() << endl;
}
