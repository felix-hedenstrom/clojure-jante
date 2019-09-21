(ns jante.cli)


(defn remove-sent-messages
  [state]
  (update state :messages (fn [messages] (filter #(not= (:recipient %) :cli) messages))))

(defn recieve
  [state]
  (let [messages (filter #(= (:recipient %) :cli) (:messages state))]
    (reduce (fn [_ message messages]
              (println message)) messages []))
  
    (print ">")
    (flush)
    (let [state (remove-sent-messages state)
          message 
          {:sender :cli
           :recipient :local
           :text (read-line)}]
    (if (empty? (:text message))
      state
      (update state :messages #(conj % message)))))  
