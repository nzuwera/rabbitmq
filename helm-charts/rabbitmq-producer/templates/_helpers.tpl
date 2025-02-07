{{/*
Expand the name of the chart.
*/}}
{{- define "rabbitmq-producer.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "rabbitmq-producer.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "rabbitmq-producer.labels" -}}
helm.sh/chart: {{ include "rabbitmq-producer.name" . }}
{{ include "rabbitmq-producer.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "rabbitmq-producer.selectorLabels" -}}
app.kubernetes.io/name: {{ include "rabbitmq-producer.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}