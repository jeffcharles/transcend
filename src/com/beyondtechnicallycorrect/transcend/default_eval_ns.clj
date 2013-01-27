(ns com.beyondtechnicallycorrect.transcend.default-eval-ns)

(defn eval-transcend-fn
  [transcend-fn]
  (try
    (binding [*ns*
      (find-ns 'com.beyondtechnicallycorrect.transcend.default-eval-ns)]
      (load-string transcend-fn))
    (catch RuntimeException e
      "#ERROR")))
