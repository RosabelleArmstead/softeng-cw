#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <iomanip>
#include "Animal.h"
#include "Cat.h"

using namespace std;


int main() {
  Cat* grandfather = new Cat("Donald", "breed", "colour", "ear type", 50, "tail colour", NULL, NULL);
  Cat* father = new Cat("Bruce", "breed", "colour", "ear type", 50, "tail colour", grandfather, NULL);
  Cat* child = new Cat("Dave", "breed2", "colour2", "ear type2", 50, "tail colour2", father, NULL);

  cout << child->getName() << "'s father is " << child->getFather()->getName() << endl;
  cout << child->getName() << "'s grandfather is " << child->getFather()->getFather()->getName() << endl;
}
