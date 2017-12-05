#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <iomanip>
#include "Animal.h"
#include "Cat.h"

using namespace std;


int main() {
  Cat* greatgreatgrandfather = new Cat("Harry", "", "", "", 0, "", NULL, NULL);
  Cat* greatgrandfather = new Cat("Dan", "", "", "", 0, "", greatgreatgrandfather, NULL);
  Cat* grandfather = new Cat("Donald", "", "", "", 0, "", greatgrandfather, NULL);
  Cat* father = new Cat("Bruce", "", "", "", 0, "", grandfather, NULL);
  Cat* child = new Cat("Dave", "", "", "", 0, "", father, NULL);

  cout << "Paternal tree of " << child->getName() << ":" << endl << child->getName() << " <-- ";
  Animal* currentChild = child;
  while (currentChild->getFather() != NULL) {
    currentChild = currentChild->getFather();
    cout << currentChild->getName() << " <-- ";
  }
  cout << "[END]" << endl;
}
