(ns com.beyondtechnicallycorrect.transcend.main
  (:use seesaw.core
        com.beyondtechnicallycorrect.transcend.ui))

(defn -main
  [& args]
  (invoke-later
    (-> (frame :title "Transcend"
          :content (scrollable (create-table))
          :on-close :exit)
      pack!
      show!)))
