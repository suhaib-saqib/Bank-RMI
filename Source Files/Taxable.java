package com.java.accounts;

/**
 * Taxable interface will be implemented to calculate tax based on interest
 * income and output the tax amount, interest rate and tax rate in a formatted
 * string
 * 
 * @author Suhaib Saqib
 * @since Feb 28, 2017
 */
public interface Taxable {
	public void calculateTax(double taxRate);

	public double getTaxAmount();

	public String createTaxStatement();
}
