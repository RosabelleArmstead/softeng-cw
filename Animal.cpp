#include "Animal.h"

Animal::Animal(string param_name, string param_breed, string param_colour, string param_earType, int param_height, string param_tailColour) {
  name = param_name;
  breed = param_breed;
  colour = param_colour;
  earType = param_earType;
  height = param_height;
  tailColour = param_tailColour;
}

string Animal::getName() { return name; }

string Animal::getBreed() { return breed; }

string Animal::getColour() { return colour; }

string Animal::getEarType() { return earType; }

int Animal::getHeight() { return height; }

string Animal::getTailColour() { return tailColour; }