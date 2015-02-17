package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */
    public Communicator() {


        public Communicator() {
            this.listnerQueue = ThreadedKernel.scheduler.newThreadQueue(true);
            this.speakerQueue = ThreadedKernel.scheduler.newThreadQueue(true);

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
        //Disable interrupts
        boolean inStatus = Machine.interrupt().disable();

        KThread thread = null;
        //While there is not listener in the listening queue
        while((thread = listnerQueue.nextThread()) == null)
        {
            //Puts speaker in the speaker queue
            this.speakerQueue.waitForAccess(KThread.currentThread());
            //puts speaker to sleep
            KThread.sleep();
        }
        buffer = word;

        //Wakes up the listner
        thread.ready();

        //Restores interrupts
        Machine.interrupt().restore(intStatus);
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */
    public int listen() {
        //Disable interrupts
        boolean intStatus = Machine.interrupt().disable();

        KThread thread = null;
       //Gets the first speaker in the speaker queue
       thread = SpeakerQueue.nextThread();

       //puts the listner in the listner queue

       //If there is a speaker
       if(thread != null){
           //Wake up that speaker
           thread.ready();
       }

       //puts the listner to sleep
       KThread.sleep();

       //Restore interrupts
       Machine.interrupt().restore(intStatus);
       return buffer;
    }

   // Tests whether this module is working

    public static void selfTest()
            {
                CommunicatorTest.runTest();
            }

}

