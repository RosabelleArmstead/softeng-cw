#include "Animal.h"
#include <iostream>
using namespace std;

Animal::Animal(string param_name, string param_breed, string param_colour,
               string param_earType, string param_height,
               string param_tailColour) {

  name = param_name;
  breed = param_breed;
  colour = param_colour;
  earType = param_earType;
  if (param_height < 0) {
    throw invalid_argument();
  } else {
      height = param_height;
  }
  tailColour = param_tailColour;
}

string Animal::getPaternalTree() {
  string fatherTree = "[END]";

  if (getFather() != NULL) {
    fatherTree = getFather()->getPaternalTree();
  }

  return name + " <-- " + fatherTree;
}

Animal::~Animal() {
  //cout << "Animal destructor" << endl;
}
