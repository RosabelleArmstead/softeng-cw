#include <string>
#include "Animal.h"
using namespace std;

Animal::Animal(string param_name, string param_breed, string param_colour, string param_earType, double param_height, string param_tailColour){
  name = param_name;
  breed = param_breed;
  colour = param_colour;
  earType = param_earType;
  height = param_height;
  tailColour = param_tailColour;
}
