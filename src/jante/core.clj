(ns jante.core
  (:require
   [jante.message :refer [get-text]]
   [jante.get :refer [get-plugins
                      get-recipient
                      get-messages
                      get-plugin-messages]]
   [jante.event :refer [trigger-event]]
   [jante.util :refer [nil-if-empty log debug]]
   [jante.construct :refer [new-bot update-internal-messages get-internal-messages send-and-recieve]]))

(defn main-loop
  [state]
  (log "Bot initialized")
  (loop [state state]
    (if-let [messages (-> (get-internal-messages state)
                          (nil-if-empty))]
      (let [message (first messages)]
        (cond
          (= (get-text message) "(quit)")
          nil
          ; This should be remade
          (= (get-text message) "(state)")
          (do (debug state)
              (recur (update-internal-messages state rest)))
          :else
              ; Run all plugins and update their internal states
          (recur (-> (trigger-event state :on-message message)
                     (update-internal-messages rest)))))
      (recur (send-and-recieve state)))))

(defn -main
  [& args]
  (print ">")
  (log "Initializing the bot")
  (main-loop (new-bot)))

