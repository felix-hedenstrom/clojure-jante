(ns jante.core
  (:require [jante.cli :refer [recieve]]
            [clojure.pprint :refer [pprint]]
            [jante.message :refer [print-message get-text]]
            [jante.get :refer [get-plugins 
                               get-recipient
                               get-messages
                               get-plugin-messages]]
            [jante.event :refer [trigger-event]] 
            [jante.util :refer [nil-if-empty]]
            [jante.construct :refer [new-bot]]
            [jante.dict :refer [parse]]))


(defn main-loop
  [state]
  (loop [state state]
    (if-let [messages (nil-if-empty (get-messages state))]
      (let [message (first messages)]
        (cond 
          (= (get-text message) "c!quit")
            nil
          ; This should be remade
          (= (get-text message) "c!state")
            (do (pprint state)
                (recur (update state :messages rest)))
          :else
            (do (if (= (get-recipient message) :cli)
                    (print-message message)
                    nil) 
              ; Run all plugins and update their internal states
              (recur (-> (trigger-event state :on-message message) 
                         (update :messages rest))))))
      (recur (recieve state)))))

(defn -main
  [& args]
  (main-loop (new-bot)))
  
