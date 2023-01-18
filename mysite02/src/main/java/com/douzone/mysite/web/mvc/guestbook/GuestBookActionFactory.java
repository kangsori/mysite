package com.douzone.mysite.web.mvc.guestbook;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("insert".equals(actionName)) {
			action=new GuestBookInsertAction();
		}else if("deleteForm".equals(actionName)) {
			action=new GuestBookDeleteFormAction();
		}else if("delete".equals(actionName)) {
			action=new GuestBookDeleteAction();
		}else {
			action=new GuestBookListAction();
		}
		return action;
	}
	
}
