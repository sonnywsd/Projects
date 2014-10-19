/*
 * Page.cpp
 *
 *  Created on: 2014?10?12?
 *      Author: sonnywsd
 */

#include "Page.h"


Page::Page(char *data)
{
	slotNum = 0;
	pagestart = data;
	freespacestart = data;
}

Page::~Page() {
	// TODO Auto-generated destructor stub

}
