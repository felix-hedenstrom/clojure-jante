(ns jante.io.io-manager)


(def default-io-manager
  {:io-type :cli
   :incoming-messages []
   :outgoing-messages ["Hello world" "This is a test" "Does this work?"]})

(defn new-io-manager
  []
  default-io-manager)

(defn get-outgoing-messages
  [io-manager]
  (:outgoing-messages io-manager))

(defn send-messages
  [io-manager]
  (do 
    (reduce (fn [_ message] (println message)) (get-outgoing-messages io-manager) [])
    (assoc io-manager :outgoing-messages []))) 

