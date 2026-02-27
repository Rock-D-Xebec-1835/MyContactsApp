package com.mycontactsapp.user.profile;
import com.mycontactsapp.user.model.*;



public class ProfileManagement {
	public static void updateName(User user, String name){
		user.updateName(name);
	}
	
	public static void changePassword(User user, String oldPassword, String newPassword){
		user.changePassword(oldPassword, newPassword);
	}
}
