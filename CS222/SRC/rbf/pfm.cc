#include "pfm.h"

PagedFileManager* PagedFileManager::_pf_manager = 0;

//#define pfm (PagedFileManager::instance())

PagedFileManager* PagedFileManager::instance()
{
    if(!_pf_manager)
        _pf_manager = new PagedFileManager();

    return _pf_manager;
}


PagedFileManager::PagedFileManager()
{
}


PagedFileManager::~PagedFileManager()
{
}

bool PagedFileManager::FileExist(const char *fileName)
{
	if (FILE *file = fopen(fileName, "r")) {
	        fclose(file);
	        return true;
	    } else {
	        return false;
	    }
}

fstream* PagedFileManager::getFileStream(string fileName)
{
	if(openedFiles.find(fileName)==openedFiles.end())
		cout<<fileName<<"not found"<<endl;
	assert(openedFiles.find(fileName)!=openedFiles.end());
	return openedFiles[fileName];
}

RC PagedFileManager::createFile(const char *fileName)
{
	if(FileExist(fileName))
		return -1;
	ofstream outfile (fileName);
	outfile.close();
	return 0;
}


RC PagedFileManager::destroyFile(const char *fileName)
{
	if(!FileExist(fileName))
			return -1;
	else
	{
		return(remove(fileName));
	}
}


RC PagedFileManager::openFile(const char *fileName, FileHandle &fileHandle)
{
	if (!FileExist(fileName))
		return -1;
	if(fileHandle.isAlreadyUsed)
		return -1;

	fileHandle.isAlreadyUsed = true;
	fileHandle.filename = fileName;

	if (filesOpenedCount.find(fileName) == filesOpenedCount.end())
	{
		filesOpenedCount[fileName] = 1;
		openedFiles[fileName] = new fstream(fileName,fstream::in | fstream::out);
	}
	else
	{
		filesOpenedCount[fileName]++;
	}
	return 0;
}


RC PagedFileManager::closeFile(FileHandle &fileHandle)
{
	if(!fileHandle.isAlreadyUsed)
		return -1;
	filesOpenedCount[fileHandle.filename]--;
	if(filesOpenedCount[fileHandle.filename]==0)
	{
		openedFiles[fileHandle.filename]->close();
		filesOpenedCount.erase(fileHandle.filename);
		openedFiles.erase(fileHandle.filename);
	}
	fileHandle.filename ="";
	fileHandle.isAlreadyUsed=false;
	return 0;
}


FileHandle::FileHandle()
{
	isAlreadyUsed=false;
}


FileHandle::~FileHandle()
{
}


RC FileHandle::readPage(PageNum pageNum, void *data)
{
	if(pageNum> getNumberOfPages())
		return -1;
	fstream *filestream = PagedFileManager::instance()->getFileStream(filename);
	filestream->seekg(pageNum * PAGE_SIZE);
	filestream->read((char *)data, PAGE_SIZE);
	return 0;
}


RC FileHandle::writePage(PageNum pageNum, const void *data)
{
	if(pageNum > getNumberOfPages())
			return -1;
	fstream *filestream = PagedFileManager::instance()->getFileStream(filename);
	filestream->seekg(pageNum * PAGE_SIZE);
	filestream->write((char*) data,PAGE_SIZE);
	return 0;
}


RC FileHandle::appendPage(const void *data)
{
    writePage(getNumberOfPages(),data);
	return 0;
}


unsigned FileHandle::getNumberOfPages()
{
	fstream *filestream = PagedFileManager::instance()->getFileStream(filename);
	filestream->seekg(0, filestream->end);
	return (filestream->tellg()/PAGE_SIZE);
}



