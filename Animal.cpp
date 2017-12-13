#include "Animal.h"
#include <iostream>
using namespace std;

Animal::Animal(string name, string breed, string colour,
               string earType, string height,
               string tailColour) : name(name), breed(breed), colour(colour),
               earType(earType), height(height),
               tailColour(tailColour) {}

void Animal::printPaternalTree() const {
  if (getFather() != NULL) {
    cout << name << " <-- ";
    getFather()->printPaternalTree();
  } else {
    cout << name << " <-- [END]";
  }
}
