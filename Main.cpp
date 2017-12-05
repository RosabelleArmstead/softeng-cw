#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;


int main() {
  bool hasExited = false;

  while (!hasExited) {
    string query;
    cout << endl << endl << "Enter the first letter of the animal group and the name of the specified one to find its paternal tree (or type exit): ";
    cin >> query;

    if (query == "exit") {
      cout << endl << endl << "Goodbye!" << endl;
      hasExited = true;

    } else {  // TODO: check validity
      char animalType = query.at(0);
      if (animalType == 'a') {
        cout << "You are searching for any animal." << endl;
      } else if (animalType == 'd') {
        cout << "You are searching for a dog." << endl;
      } else if (animalType == 'c') {
        cout << "You are searching for a cat." << endl;
      } else if (animalType == 'h') {
        cout << "You are searching for a horse." << endl;
      } else {
        cout << "Sorry, that is not a valid query. Please try again." << endl;
      }
    }
  }
}
