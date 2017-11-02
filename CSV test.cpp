#include <string>
#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector< vector<string> > parseCsv(string path) {
  ifstream file(path);
  vector< vector<string> > csv;
  string line;

  while (file >> line) {  // Loop through each line.
    vector<string> data;
    int position = line.find(',');  // Find first comma.

    while (line.find(',') != string::npos) {  // If comma found...
      data.push_back(line.substr(0, position));  // Add string from start to comma.
      line.erase(0, position + 1);  // Remove start to comma (inclusive) from line.
      position = line.find(',');  // Find next comma.
    }

    data.push_back(line);  // Add final field.
    csv.push_back(data);  // Add list of field strings.
  }

  return csv;
}

int main() {
  vector< vector<string> > horses = parseCsv("horses.csv");
  vector< vector<string> > dogs = parseCsv("dogs.csv");
  vector< vector<string> > cats = parseCsv("cats.csv");

  cout << endl << endl << "HORSES:" << endl;

  for (int i = 0; i < horses.size(); i++) {
    for (int j = 0; j < horses[i].size(); j++) {
      cout.width(horses[i].size());
      cout << horses[i][j];
    }

    cout << endl;
  }

  cout << endl << endl << "DOGS:" << endl;

  for (int i = 0; i < dogs.size(); i++) {
    for (int j = 0; j < dogs[i].size(); j++) {
      cout.width(dogs[i].size());
      cout << dogs[i][j];
    }

    cout << endl;
  }

  cout << endl << endl << "CATS:" << endl;

  for (int i = 0; i < cats.size(); i++) {
    for (int j = 0; j < cats[i].size(); j++) {
      cout.width(cats[i].size());
      cout << cats[i][j];
    }

    cout << endl;
  }
}