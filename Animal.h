// Animal.h
#ifndef ANIMAL
#define ANIMAL
#include <string>

using namespace std;

// Abstract base class for all animal types to be implemented, using derived classes, in the system.
class Animal {
  private:
    // All animal types will have these attributes.
    string name;
    string breed;
    string colour;
    string earType;
    string height;
    string tailColour;

  public:
    // Input   : All strings required for an Animal--name, breed, colour, ear type, height and tail colour.
    // Purpose : Used to create a new Animal object.
    // Output  : None.
    // Return  : N/A.
    // Throw   : None.
    Animal(string param_name, string param_breed, string param_colour, string param_earType,
           string param_height, string param_tailColour);

    // Input   : None.
    // Purpose : Gets Animal's name.
    // Output  : None.
    // Return  : Animal's name string as const.
    // Throw   : None.
    inline const string getName() const { return name; }

    // Input   : None.
    // Purpose : Gets Animal's breed.
    // Output  : None.
    // Return  : Animal's breed string as const.
    // Throw   : None.
    inline const string getBreed() const { return breed; }

    // Input   : None.
    // Purpose : Gets Animal's colour.
    // Output  : None.
    // Return  : Animal's colour string as const.
    // Throw   : None.
    inline const string getColour() const { return colour; }

    // Input   : None.
    // Purpose : Gets Animal's earType.
    // Output  : None.
    // Return  : Animal's ear type string as const.
    // Throw   : None.
    inline const string getEarType() const { return earType; }

    // Input   : None.
    // Purpose : Gets Animal's height.
    // Output  : None.
    // Return  : Animal's height string as const.
    // Throw   : None.
    inline const string getHeight() const { return height; }

    // Input   : None.
    // Purpose : Gets Animal's tailColour.
    // Output  : None.
    // Return  : Animal's tail colour string as const.
    // Throw   : None.
    inline const string getTailColour() const { return tailColour; }

    // Input   : None.
    // Purpose : Gets pointer to Animal's father of the same type. Must be implemented in derived class. Uses
    //           return type relaxation. Pure virtual.
    // Output  : None.
    // Return  : Pointer to Animal's father. May be null.
    // Throw   : None.
    virtual const Animal* getFather() const = 0;

    // Input   : None.
    // Purpose : Gets pointer to Animal's mother of the same type. Must be implemented in derived class. Uses
    //           return type relaxation. Pure virtual.
    // Output  : None.
    // Return  : Pointer to Animal's mother. May be null.
    // Throw   : None.
    virtual const Animal* getMother() const = 0;

    // Input   : None.
    // Purpose : Gets Animal's type string for printing. Must be implemented in derived class. Pure virtual.
    // Output  : None.
    // Return  : Animal's name type as const. (E.g. 'Dog'.)
    // Throw   : None.
    virtual const string getAnimalType() const = 0;

    // Input   : None.
    // Purpose : Declaration for printPaternalTree. Outputs paternal tree of Animal.
    // Output  : Paternal tree of the Animal.
    // Return  : None.
    // Throw   : None.
    void printPaternalTree() const;
};

#endif
