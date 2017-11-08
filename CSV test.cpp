#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;

vector< vector<string> > parseCsv(string path) {
  ifstream file(path);
  vector< vector<string> > csv;
  string line;

  while (file >> line) {  // Loop through each line.
    vector<string> data;
    int position = line.find(',');  // Find first comma.

    while (position != string::npos) {  // If comma found...
      data.push_back(line.substr(0, position));  // Add string from start to comma.
      line.erase(0, position + 1);  // Remove start to comma (inclusive) from line.
      position = line.find(',');  // Find next comma.
    }

    data.push_back(line);  // Add final field.
    csv.push_back(data);  // Add list of field strings.
  }

  return csv;
}

void printCsv(vector< vector<string> > csv, string kind) {
  cout << endl << endl << kind << endl;

  for (int i = 0; i < csv.size(); i++) {
    for (int j = 0; j < csv[i].size(); j++) {
      cout << left;
      cout << setw(10) << csv[i][j];
    }

    cout << endl;
  }
}

int main() {
  vector< vector<string> > horses = parseCsv("horses.csv");
  vector< vector<string> > dogs = parseCsv("dogs.csv");
  vector< vector<string> > cats = parseCsv("cats.csv");

  cout << "There are " << dogs.size() << " dog(s), ";
  cout << cats.size() << " cat(s) and ";
  cout << horses.size() << " horse(s) in the inventory, which are:" << endl << endl;

  //Possibly find a more effecient way of writing titles. Length of Tail colour a problem for printing.
  //Need to find a way to underline titles and print out animal type.
    cout << left;
    cout << setw(10) << "Name";
    cout << setw(10) << "Group";
    cout << setw(10) << "Breed";
    cout << setw(10) << "Colour";
    cout << setw(10) << "Ear Type";
    cout << setw(10) << "Height";
    cout << setw(15) << "Tail Colour";
    cout << setw(10) << "Dad";
    cout << setw(10) << "Mom" << endl;
    cout << "-----------------------------------------------------------------------------------------" << endl;

  printCsv(dogs, "DOGS: ");
  printCsv(cats, "CATS: ");
  printCsv(horses, "HORSES: ");

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
