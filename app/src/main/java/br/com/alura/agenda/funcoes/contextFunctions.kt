package br.com.alura.agenda.funcoes

import android.content.Context
import br.com.alura.agenda.R

fun Context.isLandscapeMode(): Boolean = this.resources.getBoolean(R.bool.modoPaisagem)