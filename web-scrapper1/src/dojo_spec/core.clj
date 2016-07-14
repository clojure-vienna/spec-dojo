(ns dojo-spec.core
  (:require
    [clojure.spec :as s]
    [net.cgrand.enlive-html :as html]))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

;; example
;; {:tag :span, :attrs nil,
;;        :content ({:tag :span, :attrs {:class "team-names"},
;;        :content ("Cincinnati Reds")}

(s/def ::tag keyword?)
(s/def ::attrs (s/map-of keyword? string?))
(s/def ::content (s/coll-of ::node))
(s/def ::element (s/keys :req-un [::tag]
                         :opt-un [::attrs ::content]))
(s/def ::node (s/or :element ::element :string string?))

(s/def ::standings (s/coll-of ::node))

;; example
;; {:team "test"
;;      :wins 3
;;      :losses 2}

(s/def ::standing (s/keys :req-un [::team ::wins ::losses]))