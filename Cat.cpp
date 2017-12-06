#include "Cat.h"
#include <iostream>

using namespace std;

Cat::Cat(string name, string breed, string colour, string earType, int height,
         string tailColour, Cat* father, Cat* mother) : Animal(name,
        breed, colour, earType, height, tailColour) {

  pFather = father;
  pMother = mother;
}

Cat::~Cat() {
  //cout << "Cat destructor" << endl;
}