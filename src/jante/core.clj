(ns jante.core
  (:gen-class)
  (:require
   [jante.message :refer [get-text]]
   [jante.get :refer [get-plugins
                      get-recipient
                      get-messages
                      get-plugin-messages]]
   [jante.event :refer [trigger-event]]
   [jante.util :refer [nil-if-empty log debug]]
   [jante.construct :refer [new-bot
                            update-internal-messages
                            get-internal-messages
                            pop-internal-message
                            save-bot
                            load-bot
                            send-and-recieve
                            init]]))

(def save-path "/tmp/bot.state")

(defn shutdown
  [state]
  (do
    (log "Saving bot...")
    ; Sideeffects through IO
    (save-bot state save-path)
    (log "Bot was saved!")
    (log "Shutting down.")))

(defn main-loop
  [state]
  (loop [state state]
    (let [[state message] (pop-internal-message state)]
      (if (not (nil? message))
        (cond
          (= (get-text message) "(quit)")
          (shutdown state)
          ; This should be remade
          (= (get-text message) "(state)")
          (do (debug state)
              (recur (update-internal-messages state rest)))
          :else
              ; Run all plugins and update their internal states
          (recur (-> (trigger-event state :on-message message)
                     (update-internal-messages rest))))
        (recur (send-and-recieve state))))))

(defn -main
  [& args]
  (print ">")
  (log "Trying to load the bot...")
  (let [bot
        (if-let [bot (load-bot save-path)]
            ; Add any new plugins/features if they don't already exist in the saved state
          (merge (new-bot) bot)
          (do
            (log "Could not load the bot. Creating a new..")
            (new-bot)))]
    (log "Initializing the bot")
    (let [initialized-bot (init bot)]
      (log "Bot has been initialized")
      (main-loop initialized-bot))))
