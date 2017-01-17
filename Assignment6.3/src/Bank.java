//Write an application to implement the basic functions for the Online Banking Application (Use synchronized)
public class Bank implements Runnable {

	private int balance;

	// Bank constructor
	public Bank(int balance) {
		// Set balance
		this.balance = balance;
		System.out.println("Opening Balance :" + this.balance);
	}

	// Override run() method of Runnable to execute a new thread
	public void run() {
		// Get current active thread
		//Thread activeThread = Thread.currentThread();
		
		debitAccount(20);
		creditAccount(5);
		debitAccount(15);
		creditAccount(5);
		debitAccount(10);
	}

	// Debit account if balance zero or greater after debit
	public void debitAccount(int debit) {
		Thread activeThread = Thread.currentThread();

		synchronized (this) {
			// Check to see if we can debit amount
			if (balance >= debit) {
				try {
					Thread.sleep(1000);
					System.out.println("Thread: " + activeThread.getName() + " DEBIT " + debit);
					balance -= debit;
				} catch (InterruptedException ex) {
					System.out.println(ex);
				}
			} else {
				System.out.println("Cannot debit thread named: " + activeThread.getName() + " by " + debit
						+ ". Not enough money in account!");
			}
			System.out.println("Account balance is now: " + balance);
		}
	}

	public void creditAccount(int credit) {
		Thread activeThread = Thread.currentThread();
		// Here is where out problems with concurrency are so we can synchronize
		// the following code
		synchronized (this) {
			// Move balance to i and then update from i so its non-atomic
			int i = balance;
			
			try {
				Thread.sleep(1000);
				balance = i + credit;
				System.out.println("Thread: " + activeThread.getName() + " CREDIT " + credit);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Account balance is now: " + balance);
		}
	}

	public static void main(String[] args) {
		// Create a Runnable object
		Bank b = new Bank(100);
		// Create two threads
		Thread th1 = new Thread(b, "JOHN");
		Thread th2 = new Thread(b, "MIKE");
		// Power up the threads via the start() method
		th1.start();
		th2.start();
	}
}
