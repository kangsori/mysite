package com.douzone.mysite.web.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action=new WriteFormAction();
		}else if("write".equals(actionName)) {
			action=new WriteAction();
		}else if("view".equals(actionName) || "modifyform".equals(actionName)) {
			action=new ViewAndModifyAction();
		}else if("modify".equals(actionName)) {
			action=new ModifyAction();
		}else if("delete".equals(actionName)) {
			action=new DeleteAction();
		}else {
			action=new ListAction();
		}
		return action;
	}

		
}
