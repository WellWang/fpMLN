package wzy.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import wzy.func.FileTools;
import wzy.func.InferenceTestTool;
import wzy.func.InputData;
import wzy.func.MLNProcess;
import wzy.func.OutputData;
import wzy.stat.GlobalStaticData;
import wzy.model.*;

public class ProduceFalseQueryforLNSandLSM {

	
	
	public void FilterSourceTestData(String testdbin,String testdbout,String queryfile,String query)
	{
		try {
			BufferedReader br=new BufferedReader(new FileReader(testdbin));
			String buffer=null;
			PrintStream pst=new PrintStream(testdbout);
			PrintStream psq=new PrintStream(queryfile);
			while((buffer=br.readLine())!=null)
			{
				if(buffer.length()<2)
					continue;
				
				
				String[] token=buffer.split("[()]+");
				if(token[0].equals(query))
				{
					psq.println(buffer);
				}
				else
				{
					pst.println(buffer);
				}
				
				
			}
			br.close();
			psq.close();
			pst.close();

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<HyperEdge> ReadTrueQueryasHE(String filename)
	{
		List<HyperEdge> result=new ArrayList<HyperEdge>();
		try {
			
			BufferedReader br=new BufferedReader(new FileReader(filename));

			
			String buffer=null;

			int line=0;
			while((buffer=br.readLine())!=null)
			{
				//System.out.println(buffer+"\t"+line++);
				if(buffer.length()<2)
					continue;
				String[] ss=buffer.split("[()]+");
				String rel=ss[0];
				
				
				String[] token=ss[1].split(",");
				HyperEdge he=new HyperEdge();
				
				he.rel=GlobalStaticData.relMapStoI.get(rel);
				he.value=1.;
				boolean flag=true;
				for(int i=0;i<token.length;i++)
				{
					Integer entityIndex=GlobalStaticData.entityMapStoI.get(token[i]);
					if(entityIndex==null)
					{
						flag=false;
						break;
					}
					he.nodeList.add(entityIndex);
				}
				if(flag)
				{
					result.add(he);
				}
				
			}	br.close();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void PrintTrueQuery(PrintStream ps,List<HyperEdge> heList)
	{
		for(int i=0;i<heList.size();i++)
		{
			HyperEdge he=heList.get(i);
			OutputData.PrintOneEntityHyperEdge(ps, he);
			ps.println();
		}
		ps.close();
	}
	
	public void PrintQueryWithAnswer(PrintStream ps,List<HyperEdge> heList)
	{
		for(int i=0;i<heList.size();i++)
		{
			HyperEdge he=heList.get(i);
			OutputData.PrintOneEntityHyperEdge(ps, he);
			ps.println("\t"+he.value);
		}
		ps.close();		
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		
		GlobalStaticData.IntiDataStructure();
		
		
		
		String query="_domain_topic";
		int index=4;
		
/*		FileTools.CreateNewFolder("C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query);
		FileTools.CreateNewFolder("C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index);

		
		String testdb="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\miss"+index+".db";
		String testout="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\test.db";
		String testquerytemp="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\testquerytemp";
		String true_query_file="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\true_query";
		String false_query_file="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\false_query";		
		
		String queryall="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\test.query_noresult";
		String queryallwithanswer="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\"+query+"\\"+index+"\\test.query";		
		
		String infofile="C:\\Users\\Administrator\\Desktop\\infer experiments\\citeseer data\\citeseer.ie\\citeseer.mln";*/
		
		
		FileTools.CreateNewFolder("C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query);
		//FileTools.CreateNewFolder("C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\"+index);

		
		String testdb="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\test.db";
		String testout="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\test.db";
		String testquerytemp="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\testquerytemp";
		String true_query_file="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\true_query";
		String false_query_file="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\false_query";		
		
		String queryall="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\test.query_noresult";
		String queryallwithanswer="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\testfolder\\"+query+"\\test.query";		
		
		String infofile="C:\\Users\\Administrator\\Desktop\\fpMLN实验另一部分\\mln_data\\Wordnet\\info";
				
		
/*		GlobalStaticData.relMapStoI.put(query, GlobalStaticData.relMapItoS.size());
		GlobalStaticData.relMapItoS.add(query);
		HyperEdge conceptualQuery=new HyperEdge();
		conceptualQuery.rel=GlobalStaticData.re*/
		
		ProduceFalseQueryforLNSandLSM pf=new ProduceFalseQueryforLNSandLSM();
		pf.FilterSourceTestData(testdb,
				testout,
				testquerytemp,
				query);
		
		System.out.println(GlobalStaticData.relMapStoI);
		InputData.ReadInfo(infofile,
				GlobalStaticData.relMapItoS
				, GlobalStaticData.relMapStoI, GlobalStaticData.typeMapItoS, GlobalStaticData.typeMapStoI
				, GlobalStaticData.conceptRelList);
		System.out.println(GlobalStaticData.relMapStoI);
		//System.out.println(GlobalStaticData.relMapStoI);
		
		
	
		
		
		GlobalStaticData.query=GlobalStaticData.relMapStoI.get(query);
		InputData.ReadDBgetentityInListSameTime(testout, 
				GlobalStaticData.entityMapStoI
				, GlobalStaticData.entityMapItoS, GlobalStaticData.entityToNode, GlobalStaticData.relMapStoI
				, GlobalStaticData.conceptRelList, GlobalStaticData.seed_train_list
				, query);
		
		
		
		InputData.IndexAllNodeNeighbours(GlobalStaticData.entityToNode);
		MLNProcess.KeepTypeIndexToEntitySet();
		
		GlobalStaticData.entitativeTestQueryList=pf.ReadTrueQueryasHE(testquerytemp);
		PrintStream ps_true_query=new PrintStream(true_query_file);
		pf.PrintTrueQuery(ps_true_query, GlobalStaticData.entitativeTestQueryList);
		
		List<HyperEdge> falseQueryList=InferenceTestTool.ProduceFalseQuery_return_falsequerys(2.0);
		

		PrintStream ps_false_query=new PrintStream(false_query_file);
		pf.PrintTrueQuery(ps_false_query, falseQueryList);		
		
		GlobalStaticData.entitativeTestQueryList.addAll(falseQueryList);
		
		PrintStream ps_allquery=new PrintStream(queryall);
		PrintStream ps_allquery_with_anwser=new PrintStream(queryallwithanswer);
		
		pf.PrintTrueQuery(ps_allquery, GlobalStaticData.entitativeTestQueryList);
		pf.PrintQueryWithAnswer(ps_allquery_with_anwser, GlobalStaticData.entitativeTestQueryList);
		
	}
	
}
