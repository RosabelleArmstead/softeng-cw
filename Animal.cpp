// Animal.cpp
#include "Animal.h"
#include <iostream>

// Constructor for all Animals. Data members assigned using member initialisation list.
Animal::Animal(string name, string breed, string colour, string earType, string height, string tailColour)
              : name(name), breed(breed), colour(colour), earType(earType), height(height),
                tailColour(tailColour) {}

// Input   : None.
// Purpose : Traverses Animal's paternal tree recursively, using getFather as implemented in derived
//           classes.
// Output  : Paternal tree of the Animal.
// Return  : None.
// Throw   : None.
void Animal::printPaternalTree() const {
  // If Animal has a father Animal, output Animal's name and call printPaternalTree of father Animal.
  if (getFather() != NULL) {
    cout << name << " <-- ";
    getFather()->printPaternalTree();
  } else {
    // Animal does not have a father Animal, so output Animal's name followed by END.
    cout << name << " <-- [END]";
  }
}
