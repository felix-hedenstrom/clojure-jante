(ns jante.core
  (:require [jante.cli :refer [recieve]]
            [clojure.pprint :refer [pprint]]
            [jante.message :refer [print-message]]
            [jante.get :refer [get-plugins 
                               get-recipient
                               get-messages
                               get-plugin-messages]]
            [jante.event :refer [get-all-triggered-by trigger-event]] 
            [jante.construct :refer [update-plugin new-bot]]
            [jante.dict :refer [parse]]))

(defn main-loop
  [state]
  (loop [state state]
    (let [state 
          (reduce 
            (fn [state plugin]
              (as-> (update state :messages #(concat % (get-plugin-messages state plugin))) state
                    (assoc-in state [:plugin-states plugin :messages] '()))) state (get-plugins state)) 
            messages (get-messages state)]
      
      (if (empty? messages) 
        (recur (recieve state))
        (let [message (first messages)
              {text :text} message]
          (cond 
            (= text "c!quit")
              nil
            ; This should be remade
            (= text "c!state")
              (do (pprint state)
                  (recur (update state :messages rest)))
            :else
              (do (if (= (get-recipient message) :cli)
                      (print-message message)
                      nil) 
                ; Run all plugins and update their internal states
                (recur (-> (trigger-event state :on-message message) 
                           (update :messages rest))))))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (main-loop (new-bot)))
  
