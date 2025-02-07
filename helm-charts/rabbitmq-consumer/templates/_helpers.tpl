{{/*
Expand the name of the chart.
*/}}
{{- define "rabbitmq-consumer.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
*/}}
{{- define "rabbitmq-consumer.fullname" -}}
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
{{- define "rabbitmq-consumer.labels" -}}
helm.sh/chart: {{ include "rabbitmq-consumer.name" . }}
{{ include "rabbitmq-consumer.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "rabbitmq-consumer.selectorLabels" -}}
app.kubernetes.io/name: {{ include "rabbitmq-consumer.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}