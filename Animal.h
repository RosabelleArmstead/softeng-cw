//Animal.h
#ifndef ANIMAL
#define ANIMAL
#include <string>

using namespace std;

class Animal {

  private:
    string name;
    string breed;
    string colour;
    string earType;
    string height;
    string tailColour;
  
  public:
    Animal(string param_name, string param_breed, string param_colour,
           string param_earType, string param_height, string param_tailColour);

    virtual ~Animal();

    inline string getName() { return name; }

    inline string getBreed() { return breed; }

    inline string getColour() { return colour; }

    inline string getEarType() { return earType; }

    inline string getHeight() { return height; }

    inline string getTailColour() { return tailColour; }

    virtual Animal* getFather() = 0;

    virtual Animal* getMother() = 0;

    virtual const string getAnimalType() = 0;

    string getPaternalTree();

};

#endif
