package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private List<Circuit> circuiti;
	private FormulaOneDAO dao;
	private SimpleDirectedWeightedGraph<Constructor,DefaultWeightedEdge> graph;
	private List<Constructor> costruttori;
	private List<Constructor> best;
	private int K;
	private int valore;
	
	public Model() {
		super();
		dao=new FormulaOneDAO();
	}


	public List<Circuit> getAllCircuits() {
		if(circuiti==null)
			circuiti=dao.getAllCircuitsUsati();
		
		return circuiti;
	}
	
	public void creaGrafo(Circuit c){
		graph=new SimpleDirectedWeightedGraph<Constructor,DefaultWeightedEdge> (DefaultWeightedEdge.class);
		
		costruttori=dao.getConscrtuctorsForCircuito(c);
		
		Graphs.addAllVertices(graph, costruttori);
		
		for(Constructor c1:costruttori){
			for(Constructor c2:costruttori){
				if(!c1.equals(c2)){
					Graphs.addEdgeWithVertices(graph, c1, c2,dao.getGareVinte(c,c1,c2));
				}
				
			}
			
		}
		
	}
	
	public Constructor getBest(Circuit c){
		this.creaGrafo(c);
		int risultato=Integer.MIN_VALUE;
		Constructor best=null;
		
		for(Constructor ctemp: graph.vertexSet()){
			int punteggio=0;
			for(DefaultWeightedEdge e:graph.outgoingEdgesOf(ctemp)){
				punteggio+=graph.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e:graph.incomingEdgesOf(ctemp)){
				punteggio-=graph.getEdgeWeight(e);
			}
			if(punteggio>risultato){
				risultato=punteggio;
				best=ctemp;
			}
				
			
		}
	return best;
	}

	
	public List<Constructor> dreamTeam(int k){
		List<Constructor> parziale=new ArrayList<>();
		best=new ArrayList<>();
		this.K=k;
		valore=Integer.MIN_VALUE;
		
		recursive(0,parziale);
		
		return best;
	}


	private void recursive(int livello, List<Constructor> parziale) {
		if(parziale.size()==K){
			if(this.getValoreTeam(parziale)>valore){
				valore=this.getValoreTeam(parziale);
				best.clear();
				best.addAll(parziale);
			}
			return;
		}
		
		for(Constructor ctemp:graph.vertexSet()){
			
			if(parziale.isEmpty() || ctemp.compareTo(parziale.get(parziale.size()-1))>0){
				
				parziale.add(ctemp);
				
				recursive(livello+1,parziale);
				
				parziale.remove(ctemp);
			}
		}
		
	}


	private int getValoreTeam(List<Constructor> parziale) {
		Set<Constructor> sconfitti=new HashSet<Constructor>();
		
		for(Constructor ctemp:parziale){
			for(Constructor perdente:Graphs.successorListOf(graph, ctemp)){
				if(!parziale.contains(perdente)){
					sconfitti.add(perdente);
				}
			}
		}
		
		
		
		return sconfitti.size();
	}

}
