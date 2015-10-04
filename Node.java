/**
 * @author Rui Wang
 */
public class Node {
        private String lastname;
        private String firstname;

        public Node(String aFirstname, String aLastname){
            this.lastname = aLastname;
            this.firstname = aFirstname;
        }

        public String getLastname(){
            return this.lastname;
        }

        public String getFirstname(){
            return this.firstname;
        }

        @Override
        public int hashCode() {
            int code = this.lastname.hashCode();
            code = 31 * code + this.firstname.hashCode();
            return code;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof Node)) {
                return false;
            }

            Node n = (Node)other;

            if (!this.lastname.equals(n.lastname)) {
                return false;
            }
            if (!this.firstname.equals(n.firstname)) {
                return false;
            }

            return true;
        }

}
