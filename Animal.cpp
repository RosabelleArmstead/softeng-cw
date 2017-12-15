#include "Animal.h"
#include <iostream>
using namespace std;

// Constructor to create an animal object. Explanation included in animal.h
Animal::Animal(string name, string breed, string colour,
               string earType, string height,
               string tailColour) : name(name), breed(breed), colour(colour),
               earType(earType), height(height),
               tailColour(tailColour) {}

// Allows the paternal tree of a given animal to be printed. Explanation inluded in animal.h
void Animal::printPaternalTree() const {
  // Finds an animals paternal tree using recursion
  if (getFather() != NULL) {
    cout << name << " <-- ";
    getFather()->printPaternalTree();
  } else {
    // Base case to allow the recursion to end
    cout << name << " <-- [END]";
  }
}
