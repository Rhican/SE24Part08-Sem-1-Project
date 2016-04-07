/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.manager;


import edu.nus.iss.SE24PT8.universityStore.domain.Member;
import edu.nus.iss.SE24PT8.universityStore.domain.NonMember;
import edu.nus.iss.SE24PT8.universityStore.exception.BadMemberRegistrationException;
import edu.nus.iss.SE24PT8.universityStore.util.Constants;
import edu.nus.iss.SE24PT8.universityStore.util.DataAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author THIRILWIN
 * @date 2016-March-20
 * assume member id is their NRIC number
 */
public class MemberManager implements IManager {
    
    private static MemberManager Instance=null;
    private HashMap<String,Member> memberlist=null;
    private NonMember publicMember;
    
    public static MemberManager getInstance(){
        if(Instance ==null)
        {
            Instance=new MemberManager();
        }
        return Instance;
    }
    
    private MemberManager()
    {
        ArrayList<Member> members=DataAdapter.loadMembers();
        memberlist=new HashMap<String,Member>();
        if(members != null && members.size() > 0)
        {
            for(Member m:members){
                if(m.getID() !=null)
                {
                   memberlist.put(m.getID().trim(), m);
                }
            }
        }
        else
        {
            memberlist=null;
        }
   }
    
   public HashMap<String,Member> getMembers()
   {
       if(memberlist !=null && memberlist.size() >0)
       {
          return memberlist;
       }
       return null;
   }
   
    public Member getMember(String id)
   {
       if(memberlist !=null && memberlist.size() >0)
       {
           if(memberlist.containsKey(id))
           {
                return memberlist.get(id);
           }
           else
           {
               return null;
           }
       }
       return null;
   }
   
   public void addMember(String name,String id) throws BadMemberRegistrationException{
       //NewMember => Loyalty point set as -1
       //MemberID => space will be removed.
        if(memberlist !=null && memberlist.size() >0)
        {
           if(memberlist.containsKey(id))
           {
               throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBEREXIST);
           }
        }
       
        id=id.replace(" ", "");
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(id);
        boolean b = m.find();
        if (b)
        {
           throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBERIDSPECIALCHAR);
        }
        
        Pattern numericP=Pattern.compile("[0-9]",Pattern.CASE_INSENSITIVE);
        Matcher numericm=numericP.matcher(id);
        boolean numericb=numericm.find();
        if(!numericb)
        {
        	throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_NUMERIC);
        }
        
        Matcher m2 = p.matcher(name);
        boolean b2 = m2.find();
        if (b2)
        {
           throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBERNAMESPECIALCHAR);
        }
        
        Member memberRegister=new Member(name,id.trim(),-1);
        memberlist.put(id, memberRegister);
        Save();
       
   }
   
  
   public void removeMember(String id) throws BadMemberRegistrationException
   {
       String error = null;
       if( id == null )
       {
          error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
       }
       else if(id.equals(""))
       {
           error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
       }
       else if(memberlist != null && memberlist.size() >0)
       {
           if(memberlist.containsKey(id))
           {
             memberlist.remove(id);
             Save();
           }
           else
           {
               error=Constants.CONST_MEMBER_ERR_MEMBERNOTFOUND;
           }
       }
       else
       {
           error=Constants.CONST_MEMBER_ERR_MEMBERLISTNULL;
       }
       if(error !=null)
       {
           throw new BadMemberRegistrationException(error);
       }
   }
   
    public void editMemberName(String name,String id) throws BadMemberRegistrationException{
        
       if(memberlist !=null && memberlist.size() >0)
       {
           if(memberlist.containsKey(id))
           {
               Member m= memberlist.get(id);
               if(name.length()>20)
               {
                    throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBERNAMELENGTH);
               }
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m2 = p.matcher(name);
                boolean b2 = m2.find();
                if (b2)
                {
                   throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBERNAMESPECIALCHAR);
                }
               m.setName(name);
               memberlist.put(id, m);
               Save();
           }
           else
           {
               throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_MEMBERNOTFOUND);
           }
       
       }
       
    }
   
   public boolean isFirstTransaction(String id)
   {
       if(memberlist != null && memberlist.size() >0)
       {
            if(memberlist.containsKey(id))
            {
                Member member=memberlist.get(id);
                if(member != null)
                {
                    if(member.getLoyaltyPoints() == -1)
                    {
                      return true;
                    }
                }
            }
       }
       return false;
   }
    
   public void showMembers(){
       if(memberlist != null && memberlist.size() >0)
       {
            Iterator<String> i=memberlist.keySet().iterator();
            while(i.hasNext())
            {
                Member member=memberlist.get(i.next());
                if(member != null)
                {
                    member.show();
                }
            }
       }
   }

   
   public String getNonMemberName() throws BadMemberRegistrationException
   {
	  publicMember=new NonMember(Constants.CONST_CUST_NONMEMBER_ID,Constants.CONST_CUST_NONMEMBER_NAME);
      return publicMember.getName();
   }
   
   public void addLoyaltyPoints(String memberid, int points) throws BadMemberRegistrationException
   {
     String error=null;
     if(memberlist ==null)
     {
          error=Constants.CONST_MEMBER_ERR_MEMBERLISTNULL;
     }
     else if(memberid == null)
     {
         error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
     }
     else if(memberid.equals(""))
     {
          error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
     }
     else if(!memberlist.containsKey(memberid))
     {
          error=Constants.CONST_MEMBER_ERR_MEMBERNOTFOUND;
     }
    else if( points > 5000 || points <1)
    {
        error=Constants.CONST_MEMBER_ERR_POINT;
        
    }
     else
     {
         Member m=memberlist.get(memberid);
              if(m != null)
              {
                int loyaltyPoints=m.getLoyaltyPoints();
                if(loyaltyPoints== -1)
                {
                    loyaltyPoints=points;
                }
                else
                {
                     loyaltyPoints=loyaltyPoints + points;
                }
                m.setLoyaltyPoints(loyaltyPoints);
                memberlist.put(memberid, m);
                Save();
              }
         
     }
     if(error !=null)
     {
         throw new BadMemberRegistrationException(error);
     }
   }
   
   public void redeemPoints(String memberid, int points) throws BadMemberRegistrationException
   {
     String error=null;
     if(memberlist ==null)
     {
          error=Constants.CONST_MEMBER_ERR_MEMBERLISTNULL;
     }
     else if(memberid == null)
     {
         error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
     }
     else if(memberid.equals(""))
     {
          error=Constants.CONST_MEMBER_ERR_INVALIDMEMBERID;
     }
     else if(!memberlist.containsKey(memberid))
     {
          error=Constants.CONST_MEMBER_ERR_MEMBERNOTFOUND;
     }
     else if( points > 5000 || points <1)
    {
        error=Constants.CONST_MEMBER_ERR_POINT;
        
    }
     else
     {
        Member m = memberlist.get(memberid);
        if(m != null)
        {
          int loyaltyPoints=m.getLoyaltyPoints();
          if(loyaltyPoints < 1 )
          {
             throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_NOPOINT);
          }
          if(loyaltyPoints < points)
          {
               throw new BadMemberRegistrationException(Constants.CONST_MEMBER_ERR_INSUFFICIENTPOINT);
          }
          else
          {
               loyaltyPoints=loyaltyPoints - points;
          }
          m.setLoyaltyPoints(loyaltyPoints);
          memberlist.put(memberid, m);
          Save();
        }
     }
     if(error !=null)
     {
         throw new BadMemberRegistrationException(error);
     }
   }
   
   @Override
    public void getRelatedObjects() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 

    private void Save() throws BadMemberRegistrationException {
    
      try
      {
           if( memberlist != null && memberlist.size() >0 )
            {
             ArrayList<Member> saveList= new ArrayList<Member>();
             Set mapSet=(Set) memberlist.entrySet();
             Iterator m=mapSet.iterator();
                while(m.hasNext()){
                   Map.Entry m1=(Map.Entry) m.next();
                   Member member=(Member) m1.getValue();
                   saveList.add(member);
                }
              DataAdapter.writeMembers(saveList);
            }
      }
      catch(Exception ex)
      {
           throw new BadMemberRegistrationException("Fail to save the data" + ex.toString());
      }
    }
   
   
   @Override
    public void saveData() {
        ArrayList<Member> members=new ArrayList<>();
        Iterator<Member> iter=memberlist.values().iterator();
         
        while(iter.hasNext()){
            members.add((Member)iter.next());
        }
        DataAdapter.writeMembers(members);
    }
   
   
   public Object[][] prepareMemberTableModel() {
		
	   ArrayList<Member> members=new ArrayList<>();
	   Iterator<Member> iter=memberlist.values().iterator();
	      while(iter.hasNext()){
	            members.add((Member)iter.next());
	    }
		
		Object[][] tableData = new Object[members.size()][2];
		for (int i = 0; i < members.size(); i++) {
			Member member = members.get(i);
			Object[] rowData = new Object[3];
			rowData[0] = member.getName();
			rowData[1] = member.getID();
			rowData[2] = member.getLoyaltyPoints();
			tableData[i] = rowData;
		}
		return tableData;
	}
}
