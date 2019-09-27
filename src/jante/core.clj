(ns jante.core
  (:require [jante.cli :refer [recieve]]
            [jante.message :refer [print-message]]
            [jante.event :refer [get-all-triggered-by]] 
            [jante.dict :refer [parse]]))

(def default-plugin-state 
  {
   :messages '()
   })

(defn new-bot
  []
  {
    :messages '()
    :parse-functions [parse]
    :plugin-states {:dict default-plugin-state}
   })

(defn get-plugins
  [state]
  (keys (get state :plugin-states)))

(defn get-messages
  [state]
  (:messages state))

(defn get-plugin-messages
  [state plugin]
  (get-in state [:plugin-states plugin :messages]))

(defn main-loop
  [state]
  (loop [state state]
    (let [state 
          (reduce 
            (fn [state plugin]
              (as-> (update state :messages #(concat % (get-plugin-messages state plugin))) state
                    (assoc-in state [:plugin-states plugin :messages] '()))) state (get-plugins state)) 
            messages (get-messages state)]
      
      (println "State: " state)
      (if (empty? messages) 
        (recur (recieve state))
        (let [message (first messages)
              {text :text} message]
          (if (= text "quit")
            nil
            (do (print-message message) 
                ; Run all plugins and update their internal states
                (recur (-> (update-in state [:plugin-states :dict] #(parse % message))
                           (update :messages rest))))))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (main-loop (new-bot)))
  
