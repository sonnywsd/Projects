/*
 * Page.h
 *
 *  Created on: 2014?10?12?
 *      Author: sonnywsd
 */

#ifndef PAGE_H_
#define PAGE_H_

class Page {
public:
	Page();
	virtual ~Page();

	Page(char *data);

	char *pagestart;
	int slotNum;
	char *freespacestart;

	int getslotNum();

};

#endif /* PAGE_H_ */
