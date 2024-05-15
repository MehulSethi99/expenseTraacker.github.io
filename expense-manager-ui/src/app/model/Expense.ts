export interface Expense {
  email: string,
  expenseID: string,
  title: string,
  description: string,
  date: Date,
  amount: number,
  month: string,
  year: number,
  toggleDropdow? : boolean
}