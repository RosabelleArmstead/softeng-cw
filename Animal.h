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
    // purpose --  to create a new animal object
    // input --  the different attributes that an animal must have including name, breed, colour, eartype, height and tailcolour
    // output -- None

    virtual ~Animal();
    // purpose -- to remove an animal from memory that is no loner being used
    // input -- None
    // output -- None

    inline string getName() { return name; }
    // purpose -- to retrieve the name of an animal
    // input -- None
    // output --  the name of the animal

    inline string getBreed() { return breed; }
    // purpose -- to retrieve the breed of an animal
    // input -- None
    // output --  the breed of the animal

    inline string getColour() { return colour; }
    // purpose -- to retrieve the colour of an animal
    // input -- None
    // output --  the colour of the animal

    inline string getEarType() { return earType; }
    // purpose -- to retrieve the ear type of an animal
    // input -- None
    // output --  the ear type of the animal

    inline string getHeight() { return height; }
    // purpose -- to retrieve the height of an animal
    // input -- None
    // output --  the height of the animal

    inline string getTailColour() { return tailColour; }
    // purpose -- to retrieve the tail colour of an animal
    // input -- None
    // output --  the tail colour of the animal

    virtual Animal* getFather() = 0;
    // purpose -- to retrieve the father of a specific animal
    // input -- None
    // output --  the father of an animal which is of the specific type of the animal

    virtual Animal* getMother() = 0;
    // purpose -- to retrieve the mother of a specific animal
    // input -- None
    // output --  the mother of an animal which is of the specific type of the animal

    virtual const string getAnimalType() = 0;
    // purpose -- to find the specific type of animal e.g. Cat, Dog or Horse
    // input -- None
    // output --  the type of the animal that the method has been called on e.g. Cat, Dog or Horse

    string getPaternalTree();
    // purpose -- to retrieve the paternal tree of a specific animal
    // input -- None
    // output --  the paternal tree of the animal given

};

#endif
