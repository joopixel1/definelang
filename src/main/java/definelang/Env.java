package definelang;

/**
 * Representation of an environment, which maps variables to values.
 *
 * @author hridesh
 */
public interface Env {
    Value get(String search_var);

    boolean isEmpty();

    @SuppressWarnings("serial")
    class LookupException extends RuntimeException {
        LookupException(String message) {
            super(message);
        }
    }

    class EmptyEnv implements Env {
        public Value get(String search_var) {
            throw new LookupException("No binding found for name: " + search_var);
        }

        public boolean isEmpty() {
            return true;
        }
    }

    class ExtendEnv implements Env {
        private final Env _saved_env;
        private final String _var;
        private final Value _val;

        public ExtendEnv(Env saved_env, String var, Value val) {
            _saved_env = saved_env;
            _var = var;
            _val = val;
        }

        public synchronized Value get(String search_var) {
            if (search_var.equals(_var))
                return _val;
            return _saved_env.get(search_var);
        }

        public boolean isEmpty() {
            return false;
        }

        public Env saved_env() {
            return _saved_env;
        }

        public String var() {
            return _var;
        }

        public Value val() {
            return _val;
        }
    }

    class GlobalEnv implements Env {
        private final java.util.Hashtable<String, Value> map;

        public GlobalEnv() {
            map = new java.util.Hashtable<>();
        }

        public synchronized Value get(String search_var) {
            if (map.containsKey(search_var))
                return map.get(search_var);
            throw new LookupException("No binding found for name: " + search_var);
        }

        public synchronized void extend(String var, Value val) {
            map.put(var, val);
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }
    }

}
