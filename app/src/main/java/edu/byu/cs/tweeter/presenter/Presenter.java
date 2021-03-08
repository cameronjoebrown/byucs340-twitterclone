package edu.byu.cs.tweeter.presenter;

public abstract class Presenter {

    protected View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public Presenter() {}

    public Presenter(View view) {
        this.view = view;
    }

}
