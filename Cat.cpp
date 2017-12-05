#include "Cat.h"

Cat::Cat(string name, string breed, string colour, string earType, int height, string tailColour, Cat* father, Cat* mother) : Animal(name, breed, colour, earType, height, tailColour) {
  pFather = father;
  pMother = mother;
} 

Animal* Cat::getFather() { return pFather; }

Animal* Cat::getMother() { return pMother; }

Cat::~Cat() {}
Animal::~Animal() {}