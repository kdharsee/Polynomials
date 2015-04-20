package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	
	public void Polynomial2(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	public Polynomial() {
		poly = null;
	}
	
	public Polynomial(BufferedReader br2) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br2.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		if(this == null || this.poly == null) return p;
		if(p == null || p.poly == null) return this;
		Polynomial sum = new Polynomial();
		sum.poly = new Node(0,0,null);
		Node ptr = sum.poly; 
		Node p1 = this.poly;
		Node p2 = p.poly;
		while (p1 != null && p1.term.coeff == 0){ p1 = p1.next;}
		while (p2 != null && p2.term.coeff == 0){ p2 = p2.next;}
		
		while(true){
			if (p1 == null) break;
			if (p2 == null) break;
			if (p1.term.degree < p2.term.degree){
				if (p1.term.coeff != 0){
						ptr.next = new Node(p1.term.coeff, p1.term.degree, null);
						ptr = ptr.next;
				}
				p1 = p1.next;
			}
			else if (p1.term.degree > p2.term.degree){
				if (p2.term.coeff != 0){
						ptr.next = new Node(p2.term.coeff, p2.term.degree, null);
						ptr = ptr.next;
				}
				p2 = p2.next;
			}
			else //(p1.term.degree == p2.term.degree)
			{
				if (p1.term.coeff + p2.term.coeff != 0){
					ptr.next = new Node(p1.term.coeff + p2.term.coeff, p1.term.degree, null);
					ptr = ptr.next;
				}
				p1 = p1.next;
				p2 = p2.next;
			}
		}
		while (!(p1 == null)){
			ptr.next = new Node(p1.term.coeff, p1.term.degree, null);
			ptr = ptr.next;
			p1 = p1.next;
		}
		while (!(p2 == null)){
			ptr.next = new Node(p2.term.coeff, p2.term.degree, null);
			ptr = ptr.next;
			p2 = p2.next;
		}
		sum.poly = sum.poly.next;
		return sum;
	}
	
		
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		if (this == null || this.poly == null) return null;
		if (p == null || p.poly == null) return null;
		Polynomial product = new Polynomial();
		
		product.poly = new Node(0, 0, null);
		Node p1 = this.poly;
		while (true){//for (Node p1 = this.poly; p1 != null; p1 = p1.next){	//while (p1 != null && p1.term.coeff == 0) p1 = p1.next;
			if (p1 == null)break;
			if (p1.term.coeff == 0){p1 = p1.next; continue;}
			Node p2 = p.poly;
			Node productptr = product.poly;
			while (true){
				if (p2 == null) break;
				if (p2.term.coeff == 0){
					p2 = p2.next;
					continue;
				}
				Node temp = new Node(p1.term.coeff*p2.term.coeff, p1.term.degree+p2.term.degree, null);
				productptr = add(temp, productptr);
				p2 = p2.next;
			}
			p1 = p1.next;
		
		}
		product.poly = product.poly.next;
		return product;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		if (this == null || this.poly == null) return 0;
		float result = 0;
		Node ptr = this.poly;
		while(ptr != null){
			result = result + (ptr.term.coeff*(float)(Math.pow((double)x, (double)ptr.term.degree)));
			ptr = ptr.next;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			
			return retval;
		}
	}
	private Node add(Node x, Node prev){
		Node ptr = prev;
	//	while (ptr.next != null && ptr.next.term.degree <= x.term.degree) ptr = ptr.next;
		while (true){
			if (ptr.next == null) break;
			if (ptr.next.term.degree < x.term.degree){
				ptr = ptr.next;
			}
			else break;//ptr.term.degree > x.term.degree
		}
		if (ptr.next == null){
			ptr.next = x;
			return ptr;
		}
		if (ptr.next.term.degree == x.term.degree){
			ptr.next.term.coeff = x.term.coeff+ptr.next.term.coeff;
			return ptr;
		}
		// ptr.next.term.degree < n.term.degree
		x.next = ptr.next;
		ptr.next = x;
		return ptr;		
	}
}
