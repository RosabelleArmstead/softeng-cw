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
      for (auto& animal : animals) {
        if (animal.getName() == fields[6]) {
          father = &animal;
        }
      }
    }

    if (fields[7].length() > 0) {
      for (auto& animal : animals) {
        if (animal.getName() == fields[7]) {
          mother = &animal;
        }
      }
    }

    animals.push_back(AnimalType(fields[1], fields[0], fields[2], fields[3], 0, fields[5], father, mother));
  }

  return animals;
}

int main() {
  list<Cat> cats = loadData<Cat>("data/cats.csv");
  for (auto cat : cats) {
    cout << cat.getPaternalTree() << endl;
  }
}
