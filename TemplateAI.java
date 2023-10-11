public interface TemplateAI<T> {
    /**
     * Function to calculate and return the move that the AI wants to perform.
     * T is a placeholder value for the move the AI will perform. In the end it should not use a generic type.
     *
     * @return The move that the AI has decided to make.
     */
    public T getMove();
}
