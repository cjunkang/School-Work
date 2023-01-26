//Name: Chong Jun Kang
//UOW ID:6461542
#include <iostream>
#include <string>
#include <fstream>
#include "md5.h"
#include <time.h>
#include <random>
#include <cstring>
#include <algorithm>
#include <cctype>
#include <sstream>
using namespace std;

int intClearance;
string uName;
string pW;
string confirmPw;
string storedUName;
string stored_pW;
string salt;
string saltedpW;
string salteduName;
string hashSaltedPw;
string uClearance;
string loginSalt;
string loginHashSaltPass;
string fileName;
string fileClearance;
string fileNameTrimmed;
string fileClearanceTrimmed;
string storedShadow;
string storedClearance;
vector<string> fileStore;

int mainMenu(int);
void createAccount();

string saltGenerator(string &, string &);
void writeToSaltFile();
string userClearance();
void writeToShadowFile();

void login();
void uNameLoginCheck();
string generateLoginPwSaltHash(string &, string &);
void checkPassSaltHash();
void fileSystem();
void retriveFileStore(vector<string> &);
void createFile();
void appendFile();
void readFile();
void writeFile();
void listFile();
void saveFile();
void promptExit();


// main > login/create account
// create account
// login > options > Create / Read / Write / List / Save / Exit
int main(int argc, char *argv[])
{
  if (argc == 2 && string(argv[1]) == "-i")
  {
    cout << "Creating an Account.\n";
    createAccount();
  }
  else if (argc == 1)
  {
    cout << "Logging into Account.\n";
    login();
  }
  return 0;
}

void createAccount()
{
enterUsernameAgain:
  cout << "Username : ";
  cin >> uName;
  cin.ignore();
  size_t findColon = uName.find(":");
  if (findColon == string::npos)
  {
    transform(uName.begin(), uName.end(), uName.begin(), ptr_fun<int, int>(tolower));
    string line;
    ifstream saltFile;
    bool found = false;

    saltFile.open("salt.txt");
    while (saltFile.good())
    {
      getline(saltFile, line);
      size_t pos = line.find(":");
      storedUName = line.substr(0, pos);
      if (storedUName == uName)
      {
        found = true;
        break;
      }
    }
    saltFile.close();

    if (found == false)
    {
    passworderror:
      do
      {
        cout << "Password : ";
        cin >> pW;
        cin.ignore();
        cout << "Confirm Password : ";
        cin >> confirmPw;
        cin.ignore();
        if (pW != confirmPw)
        {
          cout << "Passwords do not match, try again\n";
        }
      } while (pW != confirmPw);
      int n = pW.length();
      bool hasLower = false, hasUpper = false;
      bool hasDigit = false, specialChar = false;
      string normalChars = "abcdefghijklmnopqrstu"
                           "vwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";

      for (int i = 0; i < n; i++)
      {
        if (islower(pW[i]))
          hasLower = true;
        if (isupper(pW[i]))
          hasUpper = true;
        if (isdigit(pW[i]))
          hasDigit = true;

        size_t special = pW.find_first_not_of(normalChars);
        if (special != string::npos)
          specialChar = true;
      }
      if (hasLower && hasUpper && hasDigit &&
          specialChar && (n >= 8))
      {
        cout << "Password strength sufficient, creating account." << endl;
        saltGenerator(uName, pW);
        userClearance();
        writeToSaltFile();
        writeToShadowFile();
      }
      else
      {
        cout << "Password strength weak. Password is to be longer than 8 characters, contain 1 lowercase, 1 uppercase, 1 special character and 1 number." << endl;
        goto passworderror;
      }
    }
    else
    {
      cout << "Username already exists. Exiting Program.\n";
    }
  }
}

string saltGenerator(string &, string &)
{
  srand(time(NULL));
  for (int i = 0; i < 8; i++)
  {
    int digit = rand() % 10;
    salt += to_string(digit);
  }
  salteduName = uName + ":" + salt;
  saltedpW = pW + salt;
  hashSaltedPw = md5(saltedpW);
  return salteduName;
  return hashSaltedPw;
}

void writeToSaltFile()
{
  //Username:Salt
  ofstream writeSalt;
  writeSalt.open("salt.txt", ios::app);
  writeSalt << salteduName + "\n";
  writeSalt.close();
  cout << "Successfully written into salt file : " << salteduName << "\n";
}

void writeToShadowFile()
{
  ofstream shadowFile;
  shadowFile.open("shadow.txt", ios::app);
  string saltHash = uName + ":" + hashSaltedPw + ":" + uClearance + "\n";
  shadowFile << saltHash;
  shadowFile.close();
  cout << "Successfully written into shadow file : " << saltHash;
}

string userClearance()
{
again:
  cout << "User clearance (0 or 1 or 2 or 3) : ";
  cin >> uClearance;
  cin.ignore();
  if (uClearance != "0" && uClearance != "1" && uClearance != "2" && uClearance != "3")
  {
    cout << "Invalid clearance input. Please try again! \n";
    goto again;
  }
  else
  {
    cout << "User clearance is : " << uClearance << "\n";
  }
  return uClearance;
}

void login()
{
  string line;

  cout << "Username : ";
  cin >> uName;
  cin.ignore();
  transform(uName.begin(), uName.end(), uName.begin(), ptr_fun<int, int>(tolower));
  cout << "Password : ";
  cin >> pW;
  cin.ignore();
  cout << "\n\n";
  ifstream saltFile;
  bool found = false;
  saltFile.open("salt.txt");
  while (saltFile.good())
  {
    getline(saltFile, line);
    size_t pos = line.find(":");
    storedUName = line.substr(0, pos);
    if (storedUName == uName)
    {
      loginSalt = line.substr(pos + 1);
      //cout << loginSalt;
      break;
    }
  }
  saltFile.close();
  retriveFileStore(fileStore);
  generateLoginPwSaltHash(pW, loginSalt);
  checkPassSaltHash();
}

void retriveFileStore(vector<string> &fileVector)
{
  string line;
  ifstream file;
  file.open("FileStore.txt");
  while (file.good())
  {
    getline(file, line);
    if (line != "")
    {
      fileVector.push_back(line);
    }
  }
  file.close();
}

string generateLoginPwSaltHash(string &, string &)
{
  string loginSaltedPass = pW + loginSalt;
  loginHashSaltPass = md5(loginSaltedPass);
  return loginHashSaltPass;
}

void checkPassSaltHash()
{
  string line;
  bool found = false;

  ifstream shadowFile;
  shadowFile.open("shadow.txt");
  while (shadowFile.good())
  {
// username
    getline(shadowFile, line);
// find separator
    size_t pos = line.find(":");
// shadow hash
    storedShadow = line.substr(pos + 1);
    size_t pos2 = storedShadow.find(":");
// user clearance
    storedShadow = storedShadow.substr(0, pos2);

    storedClearance = line.substr(pos + 1);
    size_t pos3 = storedClearance.find(":");
    storedClearance = storedClearance.substr(pos3 + 1);

    if (loginHashSaltPass == storedShadow)
    {
      found = true;
      break;
    }
  }
  shadowFile.close();

  if (found == true)
  {
    cout << uName << " found in salt.txt\n";
    cout << "salt retrieved : " << loginSalt << "\n";
    cout << "hashing ...\n";
    cout << "hash value : " << loginHashSaltPass << "\n";
    cout << "Authentication for user " << uName << " complete.\n";
    cout << "The clearance for " << uName << " is " << storedClearance << "\n\n";
    fileSystem();
  }
  else
  {
    cout << "Wrong Username or Password entered. Exiting Program. Please try again.\n";
  }
}
void fileSystem()
{
  string exitProgram = "";
  do
  {
    char choice;
    cout << "\nOptions: (C)reate, (A)ppend, (R)ead, (W)rite, (L)ist, (S)ave or (E)xit.\n";
    cout << "Please enter your option : ";
    cin >> choice;
    cin.ignore();
    choice = toupper(choice); // lowercase to uppercase
    switch (choice)
    {
    case 'C':
      createFile();
      break;

    case 'A':
      appendFile();
      break;

    case 'R':
      readFile();
      break;

    case 'W':
      writeFile();
      break;

    case 'L':
      listFile();
      break;

    case 'S':
      saveFile();
      break;

    case 'E':
      exitProgram = 'E';
      promptExit();
    }
  } while (exitProgram == "");
}

void createFile()
{
  string line;
  bool found = false;
  ifstream inFile;

enterFileNameAgain:
  cout << "Filename : ";
string normalSecurity = "0123";
  cin >> fileName;
  cin.ignore();
  size_t findColon = fileName.find(":");

  if (findColon == string::npos)
  {
    transform(fileName.begin(), fileName.end(), fileName.begin(), ptr_fun<int, int>(tolower));

    inFile.open("FileStore.txt");
    while (inFile.good())
    {
      getline(inFile, line);
      size_t pos = line.find(":");
      fileNameTrimmed = line.substr(0, pos);
      fileClearanceTrimmed = line.substr(pos + 1);
      if (fileNameTrimmed == fileName)
      {
        found = true;
        break;
      }
    }
    inFile.close();

    if (found == true)
    {
      cout << "File already exists. Please try creating again.\n\n";
    }
    else
    {
    again:
	int min;
	int max;
	int range;
      cout << "Security level (0 or 1 or 2 or 3) : ";
      cin >> fileClearance;
	intClearance = stoi(fileClearance);
	if (intClearance >= 0 && intClearance <= 3)
		{ 
      		cin.ignore();
	      if (storedClearance <= fileClearance)
	      {   
		fileStore.push_back(fileName + ":" + fileClearance);
		cout << "Successfully Created File!";
	      }
      else if (storedClearance > fileClearance)
      {
        cout << "Your current clearance level:" << storedClearance << " is not allowed to write a file of level " << fileClearance << "\n";
        goto again;
      }
	}
	else
	{
	cout << "Enter a correct number from 0 to 3.";
	}
    }
  }
  else
  {
    cout << "File name cannot contain :\n\n";
    goto enterFileNameAgain;
  }
}


void appendFile()
{
string line;
  bool found = false;
  ifstream inFile;
  
  cout << "\nPlease enter Filename to append : ";
  cin >> fileName;
  cin.ignore();
  transform(fileName.begin(), fileName.end(), fileName.begin(), ptr_fun<int, int>(tolower));
  for (int i = 0; i < fileStore.size(); i++)
  {
    size_t pos = fileStore[i].find(":");
    fileNameTrimmed = fileStore[i].substr(0, pos);
    fileClearanceTrimmed = fileStore[i].substr(pos + 1);

    intClearance = stoi(fileClearanceTrimmed);

    if (fileNameTrimmed == fileName)
    {
      found = true;
      break;
    }

  }

  if (found == true)
  {
	if (intClearance >= 3)
	    {
		    if (storedClearance >= fileClearanceTrimmed)
		    {
			cout << "You have successfully appended the file!\n\n";
		    }
		    
		    else if (storedClearance < fileClearanceTrimmed) // 
		    {
		      cout << "You do not have necessary permission to append the file!\n\n";
		    }
  	}
	else if (intClearance < 3)
		{
		cout << "The file has insufficient security to be appended.";
		}
  }
  else
  {
    cout << "This file does not exist. Please input again.\n\n";
  }
}

void readFile()
{
  string line;
  bool found = false;
  ifstream inFile;

  cout << "\nPlease enter Filename to read : ";
  cin >> fileName;
  cin.ignore();
  transform(fileName.begin(), fileName.end(), fileName.begin(), ptr_fun<int, int>(tolower));
  for (int i = 0; i < fileStore.size(); i++)
  {
    size_t pos = fileStore[i].find(":");
    fileNameTrimmed = fileStore[i].substr(0, pos);
    fileClearanceTrimmed = fileStore[i].substr(pos + 1);

    intClearance = stoi(fileClearanceTrimmed);

    if (fileNameTrimmed == fileName)
    {
      found = true;
      break;
    }
  }

  if (found == true)
  {
    if(intClearance >= 0)
    {
	if (storedClearance >= fileClearanceTrimmed)
	{
      cout << "You have successfully read the file!\n\n";
	}
	    else if (storedClearance < fileClearanceTrimmed)
	    {
      cout << "You do not have necessary permission to read the file!\n\n";
    		}

  	}
}
  else
  {
    cout << "This file does not exist. Please input again.\n\n";
  }
}

void writeFile()
{
  string line;
  bool found = false;
  ifstream inFile;

  cout << "\nPlease enter Filename : ";
  cin >> fileName;
  cin.ignore();
  transform(fileName.begin(), fileName.end(), fileName.begin(), ptr_fun<int, int>(tolower));
  for (int i = 0; i < fileStore.size(); i++)
  {
    size_t pos = fileStore[i].find(":");
    fileNameTrimmed = fileStore[i].substr(0, pos);
    fileClearanceTrimmed = fileStore[i].substr(pos + 1);

    intClearance = stoi(storedClearance);
    
    if (fileNameTrimmed == fileName)
    {
      found = true;
      break;
    }
  }
  if (found == true)
  {
	if (storedClearance >= fileClearanceTrimmed)
	    {
		    if (intClearance >= 2)
		    {
		      cout << "You have successfully written to the file!\n\n";
		    }
		    else if (intClearance < 2)
		    {
		      cout << "You do not have the correct clearance to write to the file.\n\n";
		    }
	  }
	else
	{
	cout << "The file has insufficient security to be written to.";
	}
}
  else
  {
    cout << "The file name you have chosen does not exist, please try again.\n\n";
  }

}

/* Implementation of file listing */
void listFile()
{
  string line;
  bool found = false;
  ifstream inFile;
  cout << "\n############### List of existing files and clearance ###############\n";
  for (int i = 0; i < fileStore.size(); i++)
  {
    cout << fileStore[i] << "\n";
  }
  cout << "\n";
}

/* Implementation of save file function */
void saveFile()
{
  ofstream myFile;
  myFile.open("FileStore.txt", ios::out | ios::trunc);
  for (int i = 0; i < fileStore.size(); i++)
  {
    myFile << fileStore[i] << "\n";
  }
  myFile.close();
  cout << "Successfully saved file!\n\n";
  cout << "Current number of files : " << fileStore.size() << "\n";
}

/* Implementation of exit function */
void promptExit()
{
  char uInput;
  cout << "Shut down the Program? (Y)es or (N)o : ";
  cin >> uInput;
  cin.ignore();
  uInput = toupper(uInput);
  if (uInput == 'Y')
  {
    cout << "FileSystem Exiting...\n";
  }
  else
  {
    cout << "Return to Main Menu.\n\n";
    fileSystem();
  }
}